// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: YakinduReleaseTo.proto

#ifndef PROTOBUF_YakinduReleaseTo_2eproto__INCLUDED
#define PROTOBUF_YakinduReleaseTo_2eproto__INCLUDED

#include <string>

#include <google/protobuf/stubs/common.h>

#if GOOGLE_PROTOBUF_VERSION < 3004000
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please update
#error your headers.
#endif
#if 3004000 < GOOGLE_PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers.  Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/unknown_field_set.h>
#include "Enums.pb.h"
// @@protoc_insertion_point(includes)
namespace modes3 {
namespace protobuf {
class YakinduReleaseTo;
class YakinduReleaseToDefaultTypeInternal;
extern YakinduReleaseToDefaultTypeInternal _YakinduReleaseTo_default_instance_;
}  // namespace protobuf
}  // namespace modes3

namespace modes3 {
namespace protobuf {

namespace protobuf_YakinduReleaseTo_2eproto {
// Internal implementation detail -- do not call these.
struct TableStruct {
  static const ::google::protobuf::internal::ParseTableField entries[];
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[];
  static const ::google::protobuf::internal::ParseTable schema[];
  static const ::google::protobuf::uint32 offsets[];
  static const ::google::protobuf::internal::FieldMetadata field_metadata[];
  static const ::google::protobuf::internal::SerializationTable serialization_table[];
  static void InitDefaultsImpl();
};
void AddDescriptors();
void InitDefaults();
}  // namespace protobuf_YakinduReleaseTo_2eproto

// ===================================================================

class YakinduReleaseTo : public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:modes3.protobuf.YakinduReleaseTo) */ {
 public:
  YakinduReleaseTo();
  virtual ~YakinduReleaseTo();

  YakinduReleaseTo(const YakinduReleaseTo& from);

  inline YakinduReleaseTo& operator=(const YakinduReleaseTo& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  YakinduReleaseTo(YakinduReleaseTo&& from) noexcept
    : YakinduReleaseTo() {
    *this = ::std::move(from);
  }

  inline YakinduReleaseTo& operator=(YakinduReleaseTo&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  static const ::google::protobuf::Descriptor* descriptor();
  static const YakinduReleaseTo& default_instance();

  static inline const YakinduReleaseTo* internal_default_instance() {
    return reinterpret_cast<const YakinduReleaseTo*>(
               &_YakinduReleaseTo_default_instance_);
  }
  static PROTOBUF_CONSTEXPR int const kIndexInFileMessages =
    0;

  void Swap(YakinduReleaseTo* other);
  friend void swap(YakinduReleaseTo& a, YakinduReleaseTo& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline YakinduReleaseTo* New() const PROTOBUF_FINAL { return New(NULL); }

  YakinduReleaseTo* New(::google::protobuf::Arena* arena) const PROTOBUF_FINAL;
  void CopyFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void MergeFrom(const ::google::protobuf::Message& from) PROTOBUF_FINAL;
  void CopyFrom(const YakinduReleaseTo& from);
  void MergeFrom(const YakinduReleaseTo& from);
  void Clear() PROTOBUF_FINAL;
  bool IsInitialized() const PROTOBUF_FINAL;

  size_t ByteSizeLong() const PROTOBUF_FINAL;
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) PROTOBUF_FINAL;
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const PROTOBUF_FINAL;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      bool deterministic, ::google::protobuf::uint8* target) const PROTOBUF_FINAL;
  int GetCachedSize() const PROTOBUF_FINAL { return _cached_size_; }
  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const PROTOBUF_FINAL;
  void InternalSwap(YakinduReleaseTo* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return NULL;
  }
  inline void* MaybeArenaPtr() const {
    return NULL;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const PROTOBUF_FINAL;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // uint32 targetID = 1;
  void clear_targetid();
  static const int kTargetIDFieldNumber = 1;
  ::google::protobuf::uint32 targetid() const;
  void set_targetid(::google::protobuf::uint32 value);

  // .modes3.protobuf.YakinduConnectionDirection direction = 2;
  void clear_direction();
  static const int kDirectionFieldNumber = 2;
  ::modes3::protobuf::YakinduConnectionDirection direction() const;
  void set_direction(::modes3::protobuf::YakinduConnectionDirection value);

  // @@protoc_insertion_point(class_scope:modes3.protobuf.YakinduReleaseTo)
 private:

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::uint32 targetid_;
  int direction_;
  mutable int _cached_size_;
  friend struct protobuf_YakinduReleaseTo_2eproto::TableStruct;
};
// ===================================================================


// ===================================================================

#if !PROTOBUF_INLINE_NOT_IN_HEADERS
#ifdef __GNUC__
  #pragma GCC diagnostic push
  #pragma GCC diagnostic ignored "-Wstrict-aliasing"
#endif  // __GNUC__
// YakinduReleaseTo

// uint32 targetID = 1;
inline void YakinduReleaseTo::clear_targetid() {
  targetid_ = 0u;
}
inline ::google::protobuf::uint32 YakinduReleaseTo::targetid() const {
  // @@protoc_insertion_point(field_get:modes3.protobuf.YakinduReleaseTo.targetID)
  return targetid_;
}
inline void YakinduReleaseTo::set_targetid(::google::protobuf::uint32 value) {
  
  targetid_ = value;
  // @@protoc_insertion_point(field_set:modes3.protobuf.YakinduReleaseTo.targetID)
}

// .modes3.protobuf.YakinduConnectionDirection direction = 2;
inline void YakinduReleaseTo::clear_direction() {
  direction_ = 0;
}
inline ::modes3::protobuf::YakinduConnectionDirection YakinduReleaseTo::direction() const {
  // @@protoc_insertion_point(field_get:modes3.protobuf.YakinduReleaseTo.direction)
  return static_cast< ::modes3::protobuf::YakinduConnectionDirection >(direction_);
}
inline void YakinduReleaseTo::set_direction(::modes3::protobuf::YakinduConnectionDirection value) {
  
  direction_ = value;
  // @@protoc_insertion_point(field_set:modes3.protobuf.YakinduReleaseTo.direction)
}

#ifdef __GNUC__
  #pragma GCC diagnostic pop
#endif  // __GNUC__
#endif  // !PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)


}  // namespace protobuf
}  // namespace modes3

// @@protoc_insertion_point(global_scope)

#endif  // PROTOBUF_YakinduReleaseTo_2eproto__INCLUDED
