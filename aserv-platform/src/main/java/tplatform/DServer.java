/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package tplatform;

import com.absir.aserv.master.bean.JSlaveServer;
import com.absir.aserv.system.bean.value.JaLang;
import com.absir.orm.value.JaClasses;
import com.absir.validator.value.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({ "cast", "rawtypes", "serial", "unchecked" })
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-01-11")
public class DServer implements org.apache.thrift.TBase<DServer, DServer._Fields>, java.io.Serializable, Cloneable, Comparable<DServer> {

    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DServer");

    private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short) 1);

    private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short) 2);

    private static final org.apache.thrift.protocol.TField S_ADDRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("sAddress", org.apache.thrift.protocol.TType.STRING, (short) 3);

    private static final org.apache.thrift.protocol.TField PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("port", org.apache.thrift.protocol.TType.I32, (short) 4);

    private static final org.apache.thrift.protocol.TField D_ADDRESS_FIELD_DESC = new org.apache.thrift.protocol.TField("dAddress", org.apache.thrift.protocol.TType.STRING, (short) 5);

    private static final org.apache.thrift.protocol.TField WEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("weight", org.apache.thrift.protocol.TType.I32, (short) 6);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    // required
    @NotEmpty
    @JaClasses(JSlaveServer.class)
    @JaLang(value = "节点服务", tag = "slaveServer")
    protected long id;

    // required
    @NotEmpty
    @JaLang("名称")
    protected String name;

    // required
    @JaLang("服务地址")
    protected String sAddress;

    // required
    @JaLang("端口")
    protected int port;

    // optional
    @JaLang("下载地址")
    protected String dAddress;

    // optional
    @JaLang("权重")
    protected int weight;

    // isset id assignments
    private static final int __ID_ISSET_ID = 0;

    private static final int __PORT_ISSET_ID = 1;

    private static final int __WEIGHT_ISSET_ID = 2;

    protected byte __isset_bitfield = 0;

    private static final _Fields optionals[] = { _Fields.D_ADDRESS, _Fields.WEIGHT, _Fields.STATUS };

    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short) 7);

    // optional
    protected EServerStatus status;

    static {
        schemes.put(StandardScheme.class, new DServerStandardSchemeFactory());
        schemes.put(TupleScheme.class, new DServerTupleSchemeFactory());
    }

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {

        ID((short) 1, "id"), NAME((short) 2, "name"), S_ADDRESS((short) 3, "sAddress"), PORT((short) 4, "port"), D_ADDRESS((short) 5, "dAddress"), WEIGHT((short) 6, "weight"), STATUS((short) 7, "status");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
        public static _Fields findByThriftId(int fieldId) {
            switch(fieldId) {
                case // ID
                1:
                    return ID;
                case // NAME
                2:
                    return NAME;
                case // S_ADDRESS
                3:
                    return S_ADDRESS;
                case // PORT
                4:
                    return PORT;
                case // D_ADDRESS
                5:
                    return D_ADDRESS;
                case // WEIGHT
                6:
                    return WEIGHT;
                case // STATUS
                7:
                    return STATUS;
                default:
                    return null;
            }
        }

        /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null)
                throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        private final short _thriftId;

        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    public DServer() {
        this.dAddress = "";
    }

    public DServer(long id, String name, String sAddress, int port) {
        this();
        this.id = id;
        setIdIsSet(true);
        this.name = name;
        this.sAddress = sAddress;
        this.port = port;
        setPortIsSet(true);
    }

    /**
   * Performs a deep copy on <i>other</i>.
   */
    public DServer(DServer other) {
        __isset_bitfield = other.__isset_bitfield;
        this.id = other.id;
        if (other.isSetName()) {
            this.name = other.name;
        }
        if (other.isSetSAddress()) {
            this.sAddress = other.sAddress;
        }
        this.port = other.port;
        if (other.isSetDAddress()) {
            this.dAddress = other.dAddress;
        }
        this.weight = other.weight;
        if (other.isSetStatus()) {
            this.status = other.status;
        }
    }

    public DServer deepCopy() {
        return new DServer(this);
    }

    @Override
    public void clear() {
        setIdIsSet(false);
        this.id = 0;
        this.name = null;
        this.sAddress = null;
        setPortIsSet(false);
        this.port = 0;
        this.dAddress = "";
        setWeightIsSet(false);
        this.weight = 0;
        this.status = null;
    }

    public long getId() {
        return this.id;
    }

    public DServer setId(long id) {
        this.id = id;
        setIdIsSet(true);
        return this;
    }

    public void unsetId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
    }

    /** Returns true if field id is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetId() {
        return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
    }

    public void setIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
    }

    public String getName() {
        return this.name;
    }

    public DServer setName(String name) {
        this.name = name;
        return this;
    }

    public void unsetName() {
        this.name = null;
    }

    /** Returns true if field name is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetName() {
        return this.name != null;
    }

    public void setNameIsSet(boolean value) {
        if (!value) {
            this.name = null;
        }
    }

    public String getSAddress() {
        return this.sAddress;
    }

    public DServer setSAddress(String sAddress) {
        this.sAddress = sAddress;
        return this;
    }

    public void unsetSAddress() {
        this.sAddress = null;
    }

    /** Returns true if field sAddress is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetSAddress() {
        return this.sAddress != null;
    }

    public void setSAddressIsSet(boolean value) {
        if (!value) {
            this.sAddress = null;
        }
    }

    public int getPort() {
        return this.port;
    }

    public DServer setPort(int port) {
        this.port = port;
        setPortIsSet(true);
        return this;
    }

    public void unsetPort() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PORT_ISSET_ID);
    }

    /** Returns true if field port is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetPort() {
        return EncodingUtils.testBit(__isset_bitfield, __PORT_ISSET_ID);
    }

    public void setPortIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PORT_ISSET_ID, value);
    }

    public String getDAddress() {
        return this.dAddress;
    }

    public DServer setDAddress(String dAddress) {
        this.dAddress = dAddress;
        return this;
    }

    public void unsetDAddress() {
        this.dAddress = null;
    }

    /** Returns true if field dAddress is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetDAddress() {
        return this.dAddress != null;
    }

    public void setDAddressIsSet(boolean value) {
        if (!value) {
            this.dAddress = null;
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public DServer setWeight(int weight) {
        this.weight = weight;
        setWeightIsSet(true);
        return this;
    }

    public void unsetWeight() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __WEIGHT_ISSET_ID);
    }

    /** Returns true if field weight is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetWeight() {
        return EncodingUtils.testBit(__isset_bitfield, __WEIGHT_ISSET_ID);
    }

    public void setWeightIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __WEIGHT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch(field) {
            case ID:
                if (value == null) {
                    unsetId();
                } else {
                    setId((Long) value);
                }
                break;
            case NAME:
                if (value == null) {
                    unsetName();
                } else {
                    setName((String) value);
                }
                break;
            case S_ADDRESS:
                if (value == null) {
                    unsetSAddress();
                } else {
                    setSAddress((String) value);
                }
                break;
            case PORT:
                if (value == null) {
                    unsetPort();
                } else {
                    setPort((Integer) value);
                }
                break;
            case D_ADDRESS:
                if (value == null) {
                    unsetDAddress();
                } else {
                    setDAddress((String) value);
                }
                break;
            case WEIGHT:
                if (value == null) {
                    unsetWeight();
                } else {
                    setWeight((Integer) value);
                }
                break;
            case STATUS:
                if (value == null) {
                    unsetStatus();
                } else {
                    setStatus((EServerStatus) value);
                }
                break;
        }
    }

    public Object getFieldValue(_Fields field) {
        switch(field) {
            case ID:
                return getId();
            case NAME:
                return getName();
            case S_ADDRESS:
                return getSAddress();
            case PORT:
                return getPort();
            case D_ADDRESS:
                return getDAddress();
            case WEIGHT:
                return getWeight();
            case STATUS:
                return getStatus();
        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }
        switch(field) {
            case ID:
                return isSetId();
            case NAME:
                return isSetName();
            case S_ADDRESS:
                return isSetSAddress();
            case PORT:
                return isSetPort();
            case D_ADDRESS:
                return isSetDAddress();
            case WEIGHT:
                return isSetWeight();
            case STATUS:
                return isSetStatus();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof DServer)
            return this.equals((DServer) that);
        return false;
    }

    public boolean equals(DServer that) {
        if (that == null)
            return false;
        boolean this_present_id = true;
        boolean that_present_id = true;
        if (this_present_id || that_present_id) {
            if (!(this_present_id && that_present_id))
                return false;
            if (this.id != that.id)
                return false;
        }
        boolean this_present_name = true && this.isSetName();
        boolean that_present_name = true && that.isSetName();
        if (this_present_name || that_present_name) {
            if (!(this_present_name && that_present_name))
                return false;
            if (!this.name.equals(that.name))
                return false;
        }
        boolean this_present_sAddress = true && this.isSetSAddress();
        boolean that_present_sAddress = true && that.isSetSAddress();
        if (this_present_sAddress || that_present_sAddress) {
            if (!(this_present_sAddress && that_present_sAddress))
                return false;
            if (!this.sAddress.equals(that.sAddress))
                return false;
        }
        boolean this_present_port = true;
        boolean that_present_port = true;
        if (this_present_port || that_present_port) {
            if (!(this_present_port && that_present_port))
                return false;
            if (this.port != that.port)
                return false;
        }
        boolean this_present_dAddress = true && this.isSetDAddress();
        boolean that_present_dAddress = true && that.isSetDAddress();
        if (this_present_dAddress || that_present_dAddress) {
            if (!(this_present_dAddress && that_present_dAddress))
                return false;
            if (!this.dAddress.equals(that.dAddress))
                return false;
        }
        boolean this_present_weight = true && this.isSetWeight();
        boolean that_present_weight = true && that.isSetWeight();
        if (this_present_weight || that_present_weight) {
            if (!(this_present_weight && that_present_weight))
                return false;
            if (this.weight != that.weight)
                return false;
        }
        boolean this_present_status = true && this.isSetStatus();
        boolean that_present_status = true && that.isSetStatus();
        if (this_present_status || that_present_status) {
            if (!(this_present_status && that_present_status))
                return false;
            if (!this.status.equals(that.status))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();
        boolean present_id = true;
        list.add(present_id);
        if (present_id)
            list.add(id);
        boolean present_name = true && (isSetName());
        list.add(present_name);
        if (present_name)
            list.add(name);
        boolean present_sAddress = true && (isSetSAddress());
        list.add(present_sAddress);
        if (present_sAddress)
            list.add(sAddress);
        boolean present_port = true;
        list.add(present_port);
        if (present_port)
            list.add(port);
        boolean present_dAddress = true && (isSetDAddress());
        list.add(present_dAddress);
        if (present_dAddress)
            list.add(dAddress);
        boolean present_weight = true && (isSetWeight());
        list.add(present_weight);
        if (present_weight)
            list.add(weight);
        boolean present_status = true && (isSetStatus());
        list.add(present_status);
        if (present_status)
            list.add(status.getValue());
        return list.hashCode();
    }

    @Override
    public int compareTo(DServer other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }
        int lastComparison = 0;
        lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetName()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetSAddress()).compareTo(other.isSetSAddress());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetSAddress()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sAddress, other.sAddress);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetPort()).compareTo(other.isSetPort());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPort()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.port, other.port);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetDAddress()).compareTo(other.isSetDAddress());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetDAddress()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dAddress, other.dAddress);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetWeight()).compareTo(other.isSetWeight());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetWeight()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.weight, other.weight);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetStatus()).compareTo(other.isSetStatus());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetStatus()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, other.status);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DServer(");
        boolean first = true;
        sb.append("id:");
        sb.append(this.id);
        first = false;
        if (!first)
            sb.append(", ");
        sb.append("name:");
        if (this.name == null) {
            sb.append("null");
        } else {
            sb.append(this.name);
        }
        first = false;
        if (!first)
            sb.append(", ");
        sb.append("sAddress:");
        if (this.sAddress == null) {
            sb.append("null");
        } else {
            sb.append(this.sAddress);
        }
        first = false;
        if (!first)
            sb.append(", ");
        sb.append("port:");
        sb.append(this.port);
        first = false;
        if (isSetDAddress()) {
            if (!first)
                sb.append(", ");
            sb.append("dAddress:");
            if (this.dAddress == null) {
                sb.append("null");
            } else {
                sb.append(this.dAddress);
            }
            first = false;
        }
        if (isSetWeight()) {
            if (!first)
                sb.append(", ");
            sb.append("weight:");
            sb.append(this.weight);
            first = false;
        }
        if (isSetStatus()) {
            if (!first)
                sb.append(", ");
            sb.append("status:");
            if (this.status == null) {
                sb.append("null");
            } else {
                sb.append(this.status);
            }
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class DServerStandardSchemeFactory implements SchemeFactory {

        public DServerStandardScheme getScheme() {
            return new DServerStandardScheme();
        }
    }

    private static class DServerStandardScheme extends StandardScheme<DServer> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, DServer struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch(schemeField.id) {
                    case // ID
                    1:
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.id = iprot.readI64();
                            struct.setIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // NAME
                    2:
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.name = iprot.readString();
                            struct.setNameIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // S_ADDRESS
                    3:
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.sAddress = iprot.readString();
                            struct.setSAddressIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // PORT
                    4:
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.port = iprot.readI32();
                            struct.setPortIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // D_ADDRESS
                    5:
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.dAddress = iprot.readString();
                            struct.setDAddressIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // WEIGHT
                    6:
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.weight = iprot.readI32();
                            struct.setWeightIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case // STATUS
                    7:
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.status = tplatform.EServerStatus.findByValue(iprot.readI32());
                            struct.setStatusIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();
            // check for required fields of primitive type, which can't be checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, DServer struct) throws org.apache.thrift.TException {
            struct.validate();
            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ID_FIELD_DESC);
            oprot.writeI64(struct.id);
            oprot.writeFieldEnd();
            if (struct.name != null) {
                oprot.writeFieldBegin(NAME_FIELD_DESC);
                oprot.writeString(struct.name);
                oprot.writeFieldEnd();
            }
            if (struct.sAddress != null) {
                oprot.writeFieldBegin(S_ADDRESS_FIELD_DESC);
                oprot.writeString(struct.sAddress);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldBegin(PORT_FIELD_DESC);
            oprot.writeI32(struct.port);
            oprot.writeFieldEnd();
            if (struct.dAddress != null) {
                if (struct.isSetDAddress()) {
                    oprot.writeFieldBegin(D_ADDRESS_FIELD_DESC);
                    oprot.writeString(struct.dAddress);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.isSetWeight()) {
                oprot.writeFieldBegin(WEIGHT_FIELD_DESC);
                oprot.writeI32(struct.weight);
                oprot.writeFieldEnd();
            }
            if (struct.status != null) {
                if (struct.isSetStatus()) {
                    oprot.writeFieldBegin(STATUS_FIELD_DESC);
                    oprot.writeI32(struct.status.getValue());
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }
    }

    private static class DServerTupleSchemeFactory implements SchemeFactory {

        public DServerTupleScheme getScheme() {
            return new DServerTupleScheme();
        }
    }

    private static class DServerTupleScheme extends TupleScheme<DServer> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, DServer struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetId()) {
                optionals.set(0);
            }
            if (struct.isSetName()) {
                optionals.set(1);
            }
            if (struct.isSetSAddress()) {
                optionals.set(2);
            }
            if (struct.isSetPort()) {
                optionals.set(3);
            }
            if (struct.isSetDAddress()) {
                optionals.set(4);
            }
            if (struct.isSetWeight()) {
                optionals.set(5);
            }
            if (struct.isSetStatus()) {
                optionals.set(6);
            }
            oprot.writeBitSet(optionals, 7);
            if (struct.isSetId()) {
                oprot.writeI64(struct.id);
            }
            if (struct.isSetName()) {
                oprot.writeString(struct.name);
            }
            if (struct.isSetSAddress()) {
                oprot.writeString(struct.sAddress);
            }
            if (struct.isSetPort()) {
                oprot.writeI32(struct.port);
            }
            if (struct.isSetDAddress()) {
                oprot.writeString(struct.dAddress);
            }
            if (struct.isSetWeight()) {
                oprot.writeI32(struct.weight);
            }
            if (struct.isSetStatus()) {
                oprot.writeI32(struct.status.getValue());
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, DServer struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(7);
            if (incoming.get(0)) {
                struct.id = iprot.readI64();
                struct.setIdIsSet(true);
            }
            if (incoming.get(1)) {
                struct.name = iprot.readString();
                struct.setNameIsSet(true);
            }
            if (incoming.get(2)) {
                struct.sAddress = iprot.readString();
                struct.setSAddressIsSet(true);
            }
            if (incoming.get(3)) {
                struct.port = iprot.readI32();
                struct.setPortIsSet(true);
            }
            if (incoming.get(4)) {
                struct.dAddress = iprot.readString();
                struct.setDAddressIsSet(true);
            }
            if (incoming.get(5)) {
                struct.weight = iprot.readI32();
                struct.setWeightIsSet(true);
            }
            if (incoming.get(6)) {
                struct.status = tplatform.EServerStatus.findByValue(iprot.readI32());
                struct.setStatusIsSet(true);
            }
        }
    }

    public EServerStatus getStatus() {
        return this.status;
    }

    public DServer setStatus(EServerStatus status) {
        this.status = status;
        return this;
    }

    public void unsetStatus() {
        this.status = null;
    }

    /** Returns true if field status is set (has been assigned a value) and false otherwise */
    @JsonIgnore
    public boolean isSetStatus() {
        return this.status != null;
    }

    public void setStatusIsSet(boolean value) {
        if (!value) {
            this.status = null;
        }
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
        tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.DEFAULT, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.S_ADDRESS, new org.apache.thrift.meta_data.FieldMetaData("sAddress", org.apache.thrift.TFieldRequirementType.DEFAULT, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.PORT, new org.apache.thrift.meta_data.FieldMetaData("port", org.apache.thrift.TFieldRequirementType.DEFAULT, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.D_ADDRESS, new org.apache.thrift.meta_data.FieldMetaData("dAddress", org.apache.thrift.TFieldRequirementType.OPTIONAL, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.WEIGHT, new org.apache.thrift.meta_data.FieldMetaData("weight", org.apache.thrift.TFieldRequirementType.OPTIONAL, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.OPTIONAL, new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.ENUM, "EServerStatus")));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DServer.class, metaDataMap);
    }

    public DServer create() {
        return new DServer();
    }

    public DServer clone() {
        return cloneDepth(0);
    }

    public DServer cloneDepth(int _depth) {
        DServer _clone = create();
        cloneMore(_clone, _depth);
        return _clone;
    }

    public void cloneMore(DServer _clone, int _depth) {
    }
}