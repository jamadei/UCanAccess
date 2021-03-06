/*
Copyright (c) 2012 Marco Amadei.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package net.ucanaccess.jdbc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Index;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.util.OleBlob;

public class BlobKey implements Serializable {
	private static final long serialVersionUID = -8580858159403159903L;
	private HashMap<String, Object> key;
	private String tableName;
	private String columnName;
	public static final int MAX_SIZE = 4096;

	public BlobKey(HashMap<String, Object> _key, String _tableName, String _columnName) {
		super();
		this.key = _key;
		this.tableName = _tableName;
		this.columnName = _columnName;
	}

	public BlobKey(Table _table, String _columnName, Row _row) {
		this.tableName = _table.getName();
		this.columnName = _columnName;
		if (hasPrimaryKey(_table)) {
			List<? extends Index.Column> cl = _table.getPrimaryKeyIndex().getColumns();
			HashMap<String, Object> keyMap = new HashMap<String, Object>();
			for (Index.Column c : cl) {
				keyMap.put(c.getName(), _row.get(c.getName()));
			}
			this.key = keyMap;
		}
	}

	public static boolean hasPrimaryKey(Table _table) {
		for (Index idx : _table.getIndexes()) {
			if (idx.isPrimaryKey()) {
				return true;
			}
		}
		return false;
	}

	public OleBlob getOleBlob(Database db) throws UcanaccessSQLException {
		try {
			Table t = db.getTable(tableName);
			Cursor c = CursorBuilder.createPrimaryKeyCursor(t);
			return c.findFirstRow(key) ? c.getCurrentRow().getBlob(columnName) : null;
		} catch (IOException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public byte[] getBytes() throws UcanaccessSQLException {
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bais);
			oos.writeObject(this);
			oos.flush();
			return bais.toByteArray();
		} catch (IOException e) {
			throw new UcanaccessSQLException(e);
		}

	}

	public static BlobKey getBlobKey(byte[] bt) {

		ByteArrayInputStream bais = null;

		try {
			bais = new ByteArrayInputStream(bt);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object obj = ois.readObject();
			return obj instanceof BlobKey ? (BlobKey) obj : null;
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static BlobKey getBlobKey(InputStream is) {
		byte[] bt = new byte[MAX_SIZE];
		try {
			is.read(bt);
			return getBlobKey(bt);
		} catch (IOException e) {
			return null;
		}

	}

	public HashMap<String, Object> getKey() {
		return key;
	}

	public String getColumnName() {
		return columnName;
	}

}
