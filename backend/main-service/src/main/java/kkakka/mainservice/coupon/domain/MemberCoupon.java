package kkakka.mainservice.coupon.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "MemberCoupon")
public class MemberCoupon {

  @EmbeddedId
  private MemberCouponId memberCouponId;

}
