import { useState } from "react";
import DiscountEnrollTable from "./DiscountEnrollTable";
import CouponEnrollTable from "./CouponEnrollTable";

export default function PromotionEnroll() {
  const [btn, setBtn] = useState("할인");

  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "20%" }} />
            <col style={{ width: "80%" }} />
          </colgroup>

          <tbody>
            <tr style={{ height: "4vw" }}>
              <th scope="row">혜택 유형</th>
              <td>
                <div style={{ display: "flex" }}>
                  <div
                    className={`btn ${btn === "할인" ? "active" : ""}`}
                    onClick={() => setBtn("할인")}
                  >
                    할인
                  </div>
                  <div
                    className={`btn ${btn === "쿠폰" ? "active" : ""}`}
                    onClick={() => setBtn("쿠폰")}
                  >
                    쿠폰
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        {btn === "할인" ? <DiscountEnrollTable /> : <CouponEnrollTable />}
      </div>

      <style jsx>{`
        .contents {
          height: 105%;
          border: 2px solid #dedede;
          color: #2c2c2c;
          background: #fcfcfc;
          border-radius: 5em;
          font-size: 0.8rem;

          table {
            width: 90%;
            height: 10%;
            margin: auto;
            border-collapse: collapse;
            th {
              text-align: left;
            }

            tr {
              border-bottom: 1px solid #dedede;
            }

            .btn {
              border: 1px solid;
              padding: 7px 25px;
              border-radius: 1em;
              margin-right: 15px;
            }

            .active {
              background-color: #fe5c57;
              color: #fff;
              border: 1px solid #fe5c57;
              border-radius: 1em;
            }
          }

          .btnWrapper {
            display: flex;
            justify-content: center;
            margin: 20px 0;
            width: 33%;
            margin: 20px auto;
            justify-content: space-around;
          }
        }
      `}</style>
    </>
  );
}
