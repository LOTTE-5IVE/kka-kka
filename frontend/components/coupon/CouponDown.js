export function CouponDown({ handleModal }) {
  return (
    <>
      <div className="wrapper">
        <div
          style={{ display: "flex", justifyContent: "end" }}
          onClick={() => {
            handleModal();
          }}
        >
          <img width="24px" src="/common/cancel.png" />
        </div>
        <div className="container">
          <h3>전체 쿠폰</h3>
          <table>
            <colgroup>
              <col style={{ width: "25%" }} />
              <col style={{ width: "20%" }} />
              <col style={{ width: "35%" }} />
              <col style={{ width: "20%" }} />
            </colgroup>
            <thead style={{ height: "5vw" }}>
              <th>쿠폰명</th>
              <th>할인</th>
              <th>사용기한</th>
              <th>쿠폰 적용가</th>
            </thead>
            <tbody>
              <tr style={{ height: "3vw", borderBottom: "1px solid #dedede" }}>
                <td>스페셜 쿠폰 </td>
                <td>15%</td>
                <td>2022.09.15까지</td>
                <td>
                  40,000원 <img />{" "}
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <style jsx>{`
        .wrapper {
          width: 500px;
          height: 500px;
          margin-top: 5px;
          .container {
            h3 {
              width: 90%;
              margin: 0 auto 15px;
              font-weight: bold;
            }

            table {
              width: 90%;
              border-collapse: collapse;
              margin: 0 auto;
              text-align: center;

              thead {
                height: 4vw;
                border-top: 1px double;
                border-bottom: 1px double;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
