package com.drum_village_server.api.request;

import com.drum_village_server.api.common.Utils;
import com.drum_village_server.api.domain.payment.Card;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {

  private final String PASSWORD_REG_EXP = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\$\\`~!@\\$!%*\\#^?&\\(\\)\\-_=+,.\"';:\\\\|\\/<>{}\\[\\]])\\S{8,12}$";

  @NotBlank(message = "이름을 입력해 주세요.")
  private String name;

  @NotBlank(message = "이메일을 입력해 주세요.")
  @Email(message = "이메일 형식에 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호 입력해 주세요.")
  @Pattern(regexp=PASSWORD_REG_EXP, message = "비밀번호 형식이 올바르지 않습니다.")
  private String password;

  @NotBlank(message = "카드 번호를 입력해 주세요.")
  @Size(min=16, max=16, message = "카드 번호가 올바르지 않습니다.")
  private String cardNumber;

  @NotBlank(message = "카드 유효 기간을 입력해 주세요.")
  @Size(min=4, max=4, message = "카드 유효 기간이 올바르지 않습니다.")
  private String cardExpiry;

  @NotBlank(message = "생년 월일을 입력해 주세요.")
  @Size(min=6, max=6, message = "생년 월일이 올바르지 않습니다.")
  private String birth;

  @NotBlank(message = "카드 비밀번호 앞 2자리를 입력해 주세요.")
  @Size(min=2, max=2, message = "카드 빌밀번호 앞 2자리가 올바르지 않습니다.")
  private String cardPwd2digit;

  @NotBlank(message = "카드 CVC번호를 입력해 주세요.")
  @Size(min=3, max=3, message = "카드 CVC번호가 올바르지 않습니다.")
  private String cardCvc;
  public Card toCard() {
    return Card.builder()
      .cardNumber(Utils.formatCardNumber(this.cardNumber))
      .expiry(Utils.formatCardExpiry(this.cardExpiry))
      .birth(this.birth)
      .pwd2digit(this.cardPwd2digit)
      .build();
  }
}
