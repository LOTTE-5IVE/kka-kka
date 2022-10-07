export default function MyCouponCard({ test }) {
  return (
    <>
      <tr style={{ height: "3vw" }}>
        <td>스낵/쿠키 카테고리 {test}쿠폰 </td>
        <td>15% (최대 5,000원) </td>
        <td>10,000원 이상 구매시</td>
        <td>사용 가능 기간 2022-09-15</td>
      </tr>
    </>
  );
}
