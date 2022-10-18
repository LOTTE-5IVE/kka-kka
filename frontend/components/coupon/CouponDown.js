import DownloadIcon from "@mui/icons-material/Download";

export function CouponDown({ handleModal }) {
  return (
    <>
      <div className="wrapper">
        <div
          style={{
            display: "flex",
            justifyContent: "end",
            width: "98%",
            margin: "10px 0",
          }}
          onClick={() => {
            handleModal();
          }}
        >
          <img width="24px" src="/common/cancel.png" />
        </div>
        <div className="container">
          <p>상품 구매 시 사용 가능한 할인쿠폰입니다.</p>
          <table>
            <colgroup>
              <col style={{ width: "20%" }} />
              <col style={{ width: "50%" }} />
              <col style={{ width: "30%" }} />
            </colgroup>
            <thead style={{ height: "48px" }}>
              <tr>
                <th colSpan="2">상품정보</th>
                <th>상품가격</th>
              </tr>
            </thead>
            <tbody>
              <tr style={{ height: "71px", borderBottom: "1px solid #dedede" }}>
                <td>
                  <img width="64px" src="/sample.png" />
                </td>
                <td style={{ textAlign: "left" }}>ABC 초콜릿</td>
                <td>2,500원</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className="totalContainer">
          <h3>전체 쿠폰</h3>
          <table>
            <colgroup>
              <col style={{ width: "30%" }} />
              <col style={{ width: "8%" }} />
              <col style={{ width: "35%" }} />
              <col style={{ width: "20%" }} />
              <col style={{ width: "7%" }} />
            </colgroup>
            <thead style={{ height: "59px" }}>
              <tr>
                <th>쿠폰명</th>
                <th>할인</th>
                <th>사용기한</th>
                <th>쿠폰 적용가</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr style={{ height: "59px", borderBottom: "1px solid #dedede" }}>
                <td>스페셜 쿠폰 </td>
                <td>15%</td>
                <td>2022.09.15까지</td>
                <td>40,000원</td>
                <td
                  style={{
                    height: "59px",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <DownloadIcon />
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <style jsx>{`
        .wrapper {
          width: 600px;
          height: 660px;
          margin-top: 5px;
          .container {
            margin-bottom: 90px;

            p {
              width: 90%;
              margin: 0 auto 10px;
              font-weight: bold;
            }

            table {
              width: 90%;
              border-collapse: collapse;
              margin: 0 auto;
              text-align: center;

              thead {
                height: 4vw;
                border-top: 1px solid;
                border-bottom: 1px solid;
              }
            }
          }

          .totalContainer {
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
