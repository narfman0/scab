// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: network/network.proto

package com.blastedstudios.scab.network;

public final class Messages {
  private Messages() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface NetBeingOrBuilder extends
      // @@protoc_insertion_point(interface_extends:proto.NetBeing)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string name = 1;</code>
     */
    boolean hasName();
    /**
     * <code>required string name = 1;</code>
     */
    java.lang.String getName();
    /**
     * <code>required string name = 1;</code>
     */
    com.google.protobuf.ByteString
        getNameBytes();

    /**
     * <code>optional float pos_x = 2;</code>
     */
    boolean hasPosX();
    /**
     * <code>optional float pos_x = 2;</code>
     */
    float getPosX();

    /**
     * <code>optional float pos_y = 3;</code>
     */
    boolean hasPosY();
    /**
     * <code>optional float pos_y = 3;</code>
     */
    float getPosY();

    /**
     * <code>optional float vel_x = 4;</code>
     */
    boolean hasVelX();
    /**
     * <code>optional float vel_x = 4;</code>
     */
    float getVelX();

    /**
     * <code>optional float vel_y = 5;</code>
     */
    boolean hasVelY();
    /**
     * <code>optional float vel_y = 5;</code>
     */
    float getVelY();

    /**
     * <code>optional float max_hp = 6;</code>
     */
    boolean hasMaxHp();
    /**
     * <code>optional float max_hp = 6;</code>
     */
    float getMaxHp();

    /**
     * <code>optional float hp = 7;</code>
     */
    boolean hasHp();
    /**
     * <code>optional float hp = 7;</code>
     */
    float getHp();

    /**
     * <code>optional int32 currentGun = 8;</code>
     */
    boolean hasCurrentGun();
    /**
     * <code>optional int32 currentGun = 8;</code>
     */
    int getCurrentGun();
  }
  /**
   * Protobuf type {@code proto.NetBeing}
   */
  public static final class NetBeing extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:proto.NetBeing)
      NetBeingOrBuilder {
    // Use NetBeing.newBuilder() to construct.
    private NetBeing(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private NetBeing(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final NetBeing defaultInstance;
    public static NetBeing getDefaultInstance() {
      return defaultInstance;
    }

    public NetBeing getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private NetBeing(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              name_ = bs;
              break;
            }
            case 21: {
              bitField0_ |= 0x00000002;
              posX_ = input.readFloat();
              break;
            }
            case 29: {
              bitField0_ |= 0x00000004;
              posY_ = input.readFloat();
              break;
            }
            case 37: {
              bitField0_ |= 0x00000008;
              velX_ = input.readFloat();
              break;
            }
            case 45: {
              bitField0_ |= 0x00000010;
              velY_ = input.readFloat();
              break;
            }
            case 53: {
              bitField0_ |= 0x00000020;
              maxHp_ = input.readFloat();
              break;
            }
            case 61: {
              bitField0_ |= 0x00000040;
              hp_ = input.readFloat();
              break;
            }
            case 64: {
              bitField0_ |= 0x00000080;
              currentGun_ = input.readInt32();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.blastedstudios.scab.network.Messages.internal_static_proto_NetBeing_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.blastedstudios.scab.network.Messages.internal_static_proto_NetBeing_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.blastedstudios.scab.network.Messages.NetBeing.class, com.blastedstudios.scab.network.Messages.NetBeing.Builder.class);
    }

    public static com.google.protobuf.Parser<NetBeing> PARSER =
        new com.google.protobuf.AbstractParser<NetBeing>() {
      public NetBeing parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new NetBeing(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<NetBeing> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    public static final int NAME_FIELD_NUMBER = 1;
    private java.lang.Object name_;
    /**
     * <code>required string name = 1;</code>
     */
    public boolean hasName() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string name = 1;</code>
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          name_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string name = 1;</code>
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int POS_X_FIELD_NUMBER = 2;
    private float posX_;
    /**
     * <code>optional float pos_x = 2;</code>
     */
    public boolean hasPosX() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional float pos_x = 2;</code>
     */
    public float getPosX() {
      return posX_;
    }

    public static final int POS_Y_FIELD_NUMBER = 3;
    private float posY_;
    /**
     * <code>optional float pos_y = 3;</code>
     */
    public boolean hasPosY() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional float pos_y = 3;</code>
     */
    public float getPosY() {
      return posY_;
    }

    public static final int VEL_X_FIELD_NUMBER = 4;
    private float velX_;
    /**
     * <code>optional float vel_x = 4;</code>
     */
    public boolean hasVelX() {
      return ((bitField0_ & 0x00000008) == 0x00000008);
    }
    /**
     * <code>optional float vel_x = 4;</code>
     */
    public float getVelX() {
      return velX_;
    }

    public static final int VEL_Y_FIELD_NUMBER = 5;
    private float velY_;
    /**
     * <code>optional float vel_y = 5;</code>
     */
    public boolean hasVelY() {
      return ((bitField0_ & 0x00000010) == 0x00000010);
    }
    /**
     * <code>optional float vel_y = 5;</code>
     */
    public float getVelY() {
      return velY_;
    }

    public static final int MAX_HP_FIELD_NUMBER = 6;
    private float maxHp_;
    /**
     * <code>optional float max_hp = 6;</code>
     */
    public boolean hasMaxHp() {
      return ((bitField0_ & 0x00000020) == 0x00000020);
    }
    /**
     * <code>optional float max_hp = 6;</code>
     */
    public float getMaxHp() {
      return maxHp_;
    }

    public static final int HP_FIELD_NUMBER = 7;
    private float hp_;
    /**
     * <code>optional float hp = 7;</code>
     */
    public boolean hasHp() {
      return ((bitField0_ & 0x00000040) == 0x00000040);
    }
    /**
     * <code>optional float hp = 7;</code>
     */
    public float getHp() {
      return hp_;
    }

    public static final int CURRENTGUN_FIELD_NUMBER = 8;
    private int currentGun_;
    /**
     * <code>optional int32 currentGun = 8;</code>
     */
    public boolean hasCurrentGun() {
      return ((bitField0_ & 0x00000080) == 0x00000080);
    }
    /**
     * <code>optional int32 currentGun = 8;</code>
     */
    public int getCurrentGun() {
      return currentGun_;
    }

    private void initFields() {
      name_ = "";
      posX_ = 0F;
      posY_ = 0F;
      velX_ = 0F;
      velY_ = 0F;
      maxHp_ = 0F;
      hp_ = 0F;
      currentGun_ = 0;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasName()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeFloat(2, posX_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeFloat(3, posY_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        output.writeFloat(4, velX_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        output.writeFloat(5, velY_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        output.writeFloat(6, maxHp_);
      }
      if (((bitField0_ & 0x00000040) == 0x00000040)) {
        output.writeFloat(7, hp_);
      }
      if (((bitField0_ & 0x00000080) == 0x00000080)) {
        output.writeInt32(8, currentGun_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getNameBytes());
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(2, posX_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(3, posY_);
      }
      if (((bitField0_ & 0x00000008) == 0x00000008)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(4, velX_);
      }
      if (((bitField0_ & 0x00000010) == 0x00000010)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(5, velY_);
      }
      if (((bitField0_ & 0x00000020) == 0x00000020)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(6, maxHp_);
      }
      if (((bitField0_ & 0x00000040) == 0x00000040)) {
        size += com.google.protobuf.CodedOutputStream
          .computeFloatSize(7, hp_);
      }
      if (((bitField0_ & 0x00000080) == 0x00000080)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(8, currentGun_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.blastedstudios.scab.network.Messages.NetBeing parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.blastedstudios.scab.network.Messages.NetBeing prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code proto.NetBeing}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:proto.NetBeing)
        com.blastedstudios.scab.network.Messages.NetBeingOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.blastedstudios.scab.network.Messages.internal_static_proto_NetBeing_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.blastedstudios.scab.network.Messages.internal_static_proto_NetBeing_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.blastedstudios.scab.network.Messages.NetBeing.class, com.blastedstudios.scab.network.Messages.NetBeing.Builder.class);
      }

      // Construct using com.blastedstudios.scab.network.Messages.NetBeing.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        name_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        posX_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000002);
        posY_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000004);
        velX_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000008);
        velY_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000010);
        maxHp_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000020);
        hp_ = 0F;
        bitField0_ = (bitField0_ & ~0x00000040);
        currentGun_ = 0;
        bitField0_ = (bitField0_ & ~0x00000080);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.blastedstudios.scab.network.Messages.internal_static_proto_NetBeing_descriptor;
      }

      public com.blastedstudios.scab.network.Messages.NetBeing getDefaultInstanceForType() {
        return com.blastedstudios.scab.network.Messages.NetBeing.getDefaultInstance();
      }

      public com.blastedstudios.scab.network.Messages.NetBeing build() {
        com.blastedstudios.scab.network.Messages.NetBeing result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.blastedstudios.scab.network.Messages.NetBeing buildPartial() {
        com.blastedstudios.scab.network.Messages.NetBeing result = new com.blastedstudios.scab.network.Messages.NetBeing(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.name_ = name_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.posX_ = posX_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.posY_ = posY_;
        if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
          to_bitField0_ |= 0x00000008;
        }
        result.velX_ = velX_;
        if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
          to_bitField0_ |= 0x00000010;
        }
        result.velY_ = velY_;
        if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
          to_bitField0_ |= 0x00000020;
        }
        result.maxHp_ = maxHp_;
        if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
          to_bitField0_ |= 0x00000040;
        }
        result.hp_ = hp_;
        if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
          to_bitField0_ |= 0x00000080;
        }
        result.currentGun_ = currentGun_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.blastedstudios.scab.network.Messages.NetBeing) {
          return mergeFrom((com.blastedstudios.scab.network.Messages.NetBeing)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.blastedstudios.scab.network.Messages.NetBeing other) {
        if (other == com.blastedstudios.scab.network.Messages.NetBeing.getDefaultInstance()) return this;
        if (other.hasName()) {
          bitField0_ |= 0x00000001;
          name_ = other.name_;
          onChanged();
        }
        if (other.hasPosX()) {
          setPosX(other.getPosX());
        }
        if (other.hasPosY()) {
          setPosY(other.getPosY());
        }
        if (other.hasVelX()) {
          setVelX(other.getVelX());
        }
        if (other.hasVelY()) {
          setVelY(other.getVelY());
        }
        if (other.hasMaxHp()) {
          setMaxHp(other.getMaxHp());
        }
        if (other.hasHp()) {
          setHp(other.getHp());
        }
        if (other.hasCurrentGun()) {
          setCurrentGun(other.getCurrentGun());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasName()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.blastedstudios.scab.network.Messages.NetBeing parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.blastedstudios.scab.network.Messages.NetBeing) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object name_ = "";
      /**
       * <code>required string name = 1;</code>
       */
      public boolean hasName() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string name = 1;</code>
       */
      public java.lang.String getName() {
        java.lang.Object ref = name_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            name_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string name = 1;</code>
       */
      public com.google.protobuf.ByteString
          getNameBytes() {
        java.lang.Object ref = name_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          name_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder setName(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder clearName() {
        bitField0_ = (bitField0_ & ~0x00000001);
        name_ = getDefaultInstance().getName();
        onChanged();
        return this;
      }
      /**
       * <code>required string name = 1;</code>
       */
      public Builder setNameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        name_ = value;
        onChanged();
        return this;
      }

      private float posX_ ;
      /**
       * <code>optional float pos_x = 2;</code>
       */
      public boolean hasPosX() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional float pos_x = 2;</code>
       */
      public float getPosX() {
        return posX_;
      }
      /**
       * <code>optional float pos_x = 2;</code>
       */
      public Builder setPosX(float value) {
        bitField0_ |= 0x00000002;
        posX_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float pos_x = 2;</code>
       */
      public Builder clearPosX() {
        bitField0_ = (bitField0_ & ~0x00000002);
        posX_ = 0F;
        onChanged();
        return this;
      }

      private float posY_ ;
      /**
       * <code>optional float pos_y = 3;</code>
       */
      public boolean hasPosY() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional float pos_y = 3;</code>
       */
      public float getPosY() {
        return posY_;
      }
      /**
       * <code>optional float pos_y = 3;</code>
       */
      public Builder setPosY(float value) {
        bitField0_ |= 0x00000004;
        posY_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float pos_y = 3;</code>
       */
      public Builder clearPosY() {
        bitField0_ = (bitField0_ & ~0x00000004);
        posY_ = 0F;
        onChanged();
        return this;
      }

      private float velX_ ;
      /**
       * <code>optional float vel_x = 4;</code>
       */
      public boolean hasVelX() {
        return ((bitField0_ & 0x00000008) == 0x00000008);
      }
      /**
       * <code>optional float vel_x = 4;</code>
       */
      public float getVelX() {
        return velX_;
      }
      /**
       * <code>optional float vel_x = 4;</code>
       */
      public Builder setVelX(float value) {
        bitField0_ |= 0x00000008;
        velX_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float vel_x = 4;</code>
       */
      public Builder clearVelX() {
        bitField0_ = (bitField0_ & ~0x00000008);
        velX_ = 0F;
        onChanged();
        return this;
      }

      private float velY_ ;
      /**
       * <code>optional float vel_y = 5;</code>
       */
      public boolean hasVelY() {
        return ((bitField0_ & 0x00000010) == 0x00000010);
      }
      /**
       * <code>optional float vel_y = 5;</code>
       */
      public float getVelY() {
        return velY_;
      }
      /**
       * <code>optional float vel_y = 5;</code>
       */
      public Builder setVelY(float value) {
        bitField0_ |= 0x00000010;
        velY_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float vel_y = 5;</code>
       */
      public Builder clearVelY() {
        bitField0_ = (bitField0_ & ~0x00000010);
        velY_ = 0F;
        onChanged();
        return this;
      }

      private float maxHp_ ;
      /**
       * <code>optional float max_hp = 6;</code>
       */
      public boolean hasMaxHp() {
        return ((bitField0_ & 0x00000020) == 0x00000020);
      }
      /**
       * <code>optional float max_hp = 6;</code>
       */
      public float getMaxHp() {
        return maxHp_;
      }
      /**
       * <code>optional float max_hp = 6;</code>
       */
      public Builder setMaxHp(float value) {
        bitField0_ |= 0x00000020;
        maxHp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float max_hp = 6;</code>
       */
      public Builder clearMaxHp() {
        bitField0_ = (bitField0_ & ~0x00000020);
        maxHp_ = 0F;
        onChanged();
        return this;
      }

      private float hp_ ;
      /**
       * <code>optional float hp = 7;</code>
       */
      public boolean hasHp() {
        return ((bitField0_ & 0x00000040) == 0x00000040);
      }
      /**
       * <code>optional float hp = 7;</code>
       */
      public float getHp() {
        return hp_;
      }
      /**
       * <code>optional float hp = 7;</code>
       */
      public Builder setHp(float value) {
        bitField0_ |= 0x00000040;
        hp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional float hp = 7;</code>
       */
      public Builder clearHp() {
        bitField0_ = (bitField0_ & ~0x00000040);
        hp_ = 0F;
        onChanged();
        return this;
      }

      private int currentGun_ ;
      /**
       * <code>optional int32 currentGun = 8;</code>
       */
      public boolean hasCurrentGun() {
        return ((bitField0_ & 0x00000080) == 0x00000080);
      }
      /**
       * <code>optional int32 currentGun = 8;</code>
       */
      public int getCurrentGun() {
        return currentGun_;
      }
      /**
       * <code>optional int32 currentGun = 8;</code>
       */
      public Builder setCurrentGun(int value) {
        bitField0_ |= 0x00000080;
        currentGun_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 currentGun = 8;</code>
       */
      public Builder clearCurrentGun() {
        bitField0_ = (bitField0_ & ~0x00000080);
        currentGun_ = 0;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:proto.NetBeing)
    }

    static {
      defaultInstance = new NetBeing(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:proto.NetBeing)
  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_NetBeing_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_NetBeing_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\025network/network.proto\022\005proto\"\204\001\n\010NetBe" +
      "ing\022\014\n\004name\030\001 \002(\t\022\r\n\005pos_x\030\002 \001(\002\022\r\n\005pos_" +
      "y\030\003 \001(\002\022\r\n\005vel_x\030\004 \001(\002\022\r\n\005vel_y\030\005 \001(\002\022\016\n" +
      "\006max_hp\030\006 \001(\002\022\n\n\002hp\030\007 \001(\002\022\022\n\ncurrentGun\030" +
      "\010 \001(\005B+\n\037com.blastedstudios.scab.network" +
      "B\010Messages"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_proto_NetBeing_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_NetBeing_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_proto_NetBeing_descriptor,
        new java.lang.String[] { "Name", "PosX", "PosY", "VelX", "VelY", "MaxHp", "Hp", "CurrentGun", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
