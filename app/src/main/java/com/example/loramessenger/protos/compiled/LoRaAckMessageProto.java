// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/example/loramessenger/protos/lo_ra_ack_message_proto.proto

package com.example.loramessenger.protos.compiled;

public final class LoRaAckMessageProto {
  private LoRaAckMessageProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface AckMessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:AckMessage)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>optional .Metadata metadata = 1;</code>
     * @return Whether the metadata field is set.
     */
    boolean hasMetadata();
    /**
     * <code>optional .Metadata metadata = 1;</code>
     * @return The metadata.
     */
    com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata getMetadata();
    /**
     * <code>optional .Metadata metadata = 1;</code>
     */
    com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder getMetadataOrBuilder();

    /**
     * <code>optional bool received = 2;</code>
     * @return Whether the received field is set.
     */
    boolean hasReceived();
    /**
     * <code>optional bool received = 2;</code>
     * @return The received.
     */
    boolean getReceived();
  }
  /**
   * Protobuf type {@code AckMessage}
   */
  public static final class AckMessage extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:AckMessage)
      AckMessageOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use AckMessage.newBuilder() to construct.
    private AckMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private AckMessage() {
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new AckMessage();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private AckMessage(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
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
            case 10: {
              com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) != 0)) {
                subBuilder = metadata_.toBuilder();
              }
              metadata_ = input.readMessage(com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(metadata_);
                metadata_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              received_ = input.readBool();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.internal_static_AckMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.internal_static_AckMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.class, com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.Builder.class);
    }

    private int bitField0_;
    public static final int METADATA_FIELD_NUMBER = 1;
    private com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata metadata_;
    /**
     * <code>optional .Metadata metadata = 1;</code>
     * @return Whether the metadata field is set.
     */
    @java.lang.Override
    public boolean hasMetadata() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>optional .Metadata metadata = 1;</code>
     * @return The metadata.
     */
    @java.lang.Override
    public com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata getMetadata() {
      return metadata_ == null ? com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.getDefaultInstance() : metadata_;
    }
    /**
     * <code>optional .Metadata metadata = 1;</code>
     */
    @java.lang.Override
    public com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder getMetadataOrBuilder() {
      return metadata_ == null ? com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.getDefaultInstance() : metadata_;
    }

    public static final int RECEIVED_FIELD_NUMBER = 2;
    private boolean received_;
    /**
     * <code>optional bool received = 2;</code>
     * @return Whether the received field is set.
     */
    @java.lang.Override
    public boolean hasReceived() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional bool received = 2;</code>
     * @return The received.
     */
    @java.lang.Override
    public boolean getReceived() {
      return received_;
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeMessage(1, getMetadata());
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        output.writeBool(2, received_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getMetadata());
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBoolSize(2, received_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage)) {
        return super.equals(obj);
      }
      com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage other = (com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage) obj;

      if (hasMetadata() != other.hasMetadata()) return false;
      if (hasMetadata()) {
        if (!getMetadata()
            .equals(other.getMetadata())) return false;
      }
      if (hasReceived() != other.hasReceived()) return false;
      if (hasReceived()) {
        if (getReceived()
            != other.getReceived()) return false;
      }
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasMetadata()) {
        hash = (37 * hash) + METADATA_FIELD_NUMBER;
        hash = (53 * hash) + getMetadata().hashCode();
      }
      if (hasReceived()) {
        hash = (37 * hash) + RECEIVED_FIELD_NUMBER;
        hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
            getReceived());
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code AckMessage}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:AckMessage)
        com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.internal_static_AckMessage_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.internal_static_AckMessage_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.class, com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.Builder.class);
      }

      // Construct using com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
          getMetadataFieldBuilder();
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (metadataBuilder_ == null) {
          metadata_ = null;
        } else {
          metadataBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        received_ = false;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.internal_static_AckMessage_descriptor;
      }

      @java.lang.Override
      public com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage getDefaultInstanceForType() {
        return com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.getDefaultInstance();
      }

      @java.lang.Override
      public com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage build() {
        com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage buildPartial() {
        com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage result = new com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          if (metadataBuilder_ == null) {
            result.metadata_ = metadata_;
          } else {
            result.metadata_ = metadataBuilder_.build();
          }
          to_bitField0_ |= 0x00000001;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.received_ = received_;
          to_bitField0_ |= 0x00000002;
        }
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage) {
          return mergeFrom((com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage other) {
        if (other == com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage.getDefaultInstance()) return this;
        if (other.hasMetadata()) {
          mergeMetadata(other.getMetadata());
        }
        if (other.hasReceived()) {
          setReceived(other.getReceived());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata metadata_;
      private com.google.protobuf.SingleFieldBuilderV3<
          com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata, com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder, com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder> metadataBuilder_;
      /**
       * <code>optional .Metadata metadata = 1;</code>
       * @return Whether the metadata field is set.
       */
      public boolean hasMetadata() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       * @return The metadata.
       */
      public com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata getMetadata() {
        if (metadataBuilder_ == null) {
          return metadata_ == null ? com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.getDefaultInstance() : metadata_;
        } else {
          return metadataBuilder_.getMessage();
        }
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public Builder setMetadata(com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata value) {
        if (metadataBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          metadata_ = value;
          onChanged();
        } else {
          metadataBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public Builder setMetadata(
          com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder builderForValue) {
        if (metadataBuilder_ == null) {
          metadata_ = builderForValue.build();
          onChanged();
        } else {
          metadataBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public Builder mergeMetadata(com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata value) {
        if (metadataBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0) &&
              metadata_ != null &&
              metadata_ != com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.getDefaultInstance()) {
            metadata_ =
              com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.newBuilder(metadata_).mergeFrom(value).buildPartial();
          } else {
            metadata_ = value;
          }
          onChanged();
        } else {
          metadataBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public Builder clearMetadata() {
        if (metadataBuilder_ == null) {
          metadata_ = null;
          onChanged();
        } else {
          metadataBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder getMetadataBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getMetadataFieldBuilder().getBuilder();
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      public com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder getMetadataOrBuilder() {
        if (metadataBuilder_ != null) {
          return metadataBuilder_.getMessageOrBuilder();
        } else {
          return metadata_ == null ?
              com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.getDefaultInstance() : metadata_;
        }
      }
      /**
       * <code>optional .Metadata metadata = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata, com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder, com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder> 
          getMetadataFieldBuilder() {
        if (metadataBuilder_ == null) {
          metadataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata, com.example.loramessenger.protos.compiled.LoRaMetadataProto.Metadata.Builder, com.example.loramessenger.protos.compiled.LoRaMetadataProto.MetadataOrBuilder>(
                  getMetadata(),
                  getParentForChildren(),
                  isClean());
          metadata_ = null;
        }
        return metadataBuilder_;
      }

      private boolean received_ ;
      /**
       * <code>optional bool received = 2;</code>
       * @return Whether the received field is set.
       */
      @java.lang.Override
      public boolean hasReceived() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>optional bool received = 2;</code>
       * @return The received.
       */
      @java.lang.Override
      public boolean getReceived() {
        return received_;
      }
      /**
       * <code>optional bool received = 2;</code>
       * @param value The received to set.
       * @return This builder for chaining.
       */
      public Builder setReceived(boolean value) {
        bitField0_ |= 0x00000002;
        received_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool received = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearReceived() {
        bitField0_ = (bitField0_ & ~0x00000002);
        received_ = false;
        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:AckMessage)
    }

    // @@protoc_insertion_point(class_scope:AckMessage)
    private static final com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage();
    }

    public static com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<AckMessage>
        PARSER = new com.google.protobuf.AbstractParser<AckMessage>() {
      @java.lang.Override
      public AckMessage parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new AckMessage(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<AckMessage> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<AckMessage> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.example.loramessenger.protos.compiled.LoRaAckMessageProto.AckMessage getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_AckMessage_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_AckMessage_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n>com/example/loramessenger/protos/lo_ra" +
      "_ack_message_proto.proto\032;com/example/lo" +
      "ramessenger/protos/lo_ra_metadata_proto." +
      "proto\"_\n\nAckMessage\022 \n\010metadata\030\001 \001(\0132\t." +
      "MetadataH\000\210\001\001\022\025\n\010received\030\002 \001(\010H\001\210\001\001B\013\n\t" +
      "_metadataB\013\n\t_receivedB+\n)com.example.lo" +
      "ramessenger.protos.compiledb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.example.loramessenger.protos.compiled.LoRaMetadataProto.getDescriptor(),
        });
    internal_static_AckMessage_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_AckMessage_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_AckMessage_descriptor,
        new java.lang.String[] { "Metadata", "Received", "Metadata", "Received", });
    com.example.loramessenger.protos.compiled.LoRaMetadataProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
