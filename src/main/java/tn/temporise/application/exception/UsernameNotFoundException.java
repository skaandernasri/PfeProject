package tn.temporise.application.exception;

import lombok.Getter;

@Getter
public class UsernameNotFoundException extends RuntimeException {
  private final String code;
  public UsernameNotFoundException(String message,String code) {
    super(message);
    this.code=code;
  }


}
