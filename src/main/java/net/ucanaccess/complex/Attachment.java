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
package net.ucanaccess.complex;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import com.healthmarketscience.jackcess.complex.ComplexValue;

public class Attachment extends ComplexBase {
    private static final long serialVersionUID = 1L;
    private String            url;
    private String            name;
    private String            type;
    private byte[]            data;
    private LocalDateTime     timeStamp;
    private Integer           flags;

    public Attachment(com.healthmarketscience.jackcess.complex.Attachment atc) throws IOException {
        super(atc);
        this.url = atc.getFileUrl();
        this.name = atc.getFileName();
        this.type = atc.getFileType();
        this.data = atc.getFileData();
        this.timeStamp = atc.getFileLocalTimeStamp();
        this.flags = atc.getFileFlags();
    }

    public Attachment(ComplexValue.Id id, String tableName, String columnName, String _url, String _name, String _type,
            byte[] _data, LocalDateTime _timeStamp, Integer _flags) {
        super(id, tableName, columnName);
        this.url = _url;
        this.name = _name;
        this.type = _type;
        this.data = _data;
        this.timeStamp = _timeStamp;
        this.flags = _flags;
    }

    public Attachment(String _url, String _name, String _type, byte[] _data, LocalDateTime _timeStamp, Integer _flags) {
        this(CREATE_ID, null, null, _url, _name, _type, _data, _timeStamp, _flags);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        // if (!super.equals(obj))
        // return false;
        if (getClass() != obj.getClass()) {
            return false;
        }
        Attachment other = (Attachment) obj;
        if (!Arrays.equals(data, other.data)) {
            return false;
        }
        if (flags == null) {
            if (other.flags != null) {
                return false;
            }
        } else if (!flags.equals(other.flags)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (timeStamp == null) {
            if (other.timeStamp != null) {
                return false;
            }
        } else if (!timeStamp.equals(other.timeStamp)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        return true;
    }

    public byte[] getData() {
        return data;
    }

    public Integer getFlags() {
        return flags;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Arrays.hashCode(data);
        result = prime * result + ((flags == null) ? 0 : flags.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    public void setData(byte[] _data) {
        this.data = _data;
    }

    public void setFlags(Integer _flags) {
        this.flags = _flags;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setTimeStamp(LocalDateTime _timeStamp) {
        this.timeStamp = _timeStamp;
    }

    public void setType(String _type) {
        this.type = _type;
    }

    public void setUrl(String _url) {
        this.url = _url;
    }

    @Override
    public String toString() {
        return "Attachment [url=" + url + ", name=" + name + ", type=" + type + ", data=" + Arrays.toString(data)
                + ", timeStamp=" + timeStamp + ", flags=" + flags + "]";
    }

}
