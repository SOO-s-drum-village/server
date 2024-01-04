package com.drum_village_server.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@Builder
public class EnumResponse<T> {

  private T data;

  private EnumResponse(T data){
    this.data=data;
  }

  public static <T> EnumResponse<T> of(T data) {
    return new EnumResponse<>(data);
  }
}
