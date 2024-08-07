// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.remote;

import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class PresentableId implements Cloneable {
  private final @Nullable String myId;
  private final @Nullable String myName;

  @Contract("null, null -> fail")
  private PresentableId(@Nullable String id, @Nullable String name) {
    if (name == null && id == null) {
      throw new IllegalStateException("Either id or name should be provided");
    }
    myId = id;
    myName = name;
  }

  public @Nullable String getId() {
    return myId;
  }

  public @Nullable String getName() {
    return myName;
  }

  public boolean hasId() {
    return myId != null;
  }

  public boolean hasName() {
    return myName != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PresentableId id = (PresentableId)o;
    return Objects.equals(myId, id.myId) && Objects.equals(myName, id.myName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(myId, myName);
  }

  @Override
  public PresentableId clone() {
    try {
      return (PresentableId)super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return "PresentableId{myId='" + myId + "', myName='" + myName + "'}";
  }

  @Contract("_, !null->!null")
  public static @Nullable PresentableId createId(@Nullable String id, @Nullable String name) {
    id = StringUtil.nullize(id);
    name = StringUtil.nullize(name);
    return id == null && name == null ? null : new PresentableId(id, name);
  }
}
