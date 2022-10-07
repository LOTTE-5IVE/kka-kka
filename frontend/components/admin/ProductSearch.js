import { AdminButton } from "./AdminButton";

export default function ProductSearch() {
  return (
    <>
      <div className="contents">
        <table>
          <colgroup>
            <col style={{ width: "5%" }} />
            <col style={{ width: "20%" }} />
            <col style={{ width: "20%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "10%" }} />
            <col style={{ width: "12%" }} />
            <col style={{ width: "5%" }} />
            <col style={{ width: "5%" }} />
          </colgroup>
          <thead>
            <th>대표사진</th>
            <th>상품 이름</th>
            <th>상세옵션</th>
            <th>가격</th>
            <th>재고</th>
            <th>카테고리</th>
            <th>수정</th>
            <th>상품 페이지</th>
          </thead>
          <tbody>
            <tr style={{ height: "5vw" }}>
              <td>
                <img width="60px" src="/sample.png" />
              </td>
              <td>쿠키 어쩌구 저쩌구 샌드</td>
              <td>600g x 2개 1200g</td>
              <td>10,000원</td>
              <td>130</td>
              <td>스낵/쿠키</td>
              <td>
                <AdminButton context="수정" color="#F21D2F" />
              </td>
              <td>
                <AdminButton context="이동" color="#05C7F2" />
              </td>
            </tr>
            <tr style={{ height: "5vw" }}>
              <td>
                <img width="60px" src="/sample.png" />
              </td>
              <td>쿠키 어쩌구 저쩌구 샌드</td>
              <td>600g x 2개 1200g</td>
              <td>10,000원</td>
              <td>130</td>
              <td>스낵/쿠키</td>
              <td>
                <AdminButton context="수정" color="#F21D2F" />
              </td>
              <td>
                <AdminButton context="이동" color="#05C7F2" />
              </td>
            </tr>
            <tr style={{ height: "100%" }}>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>

      <style jsx>{`
        .contents {
          height: 100%;
          border: 2px solid #dedede;
          color: #7a7a7a;

          table {
            height: 100%;
            width: 100%;
            text-align: center;
            border-collapse: collapse;

            td {
              border: 1px solid #dedede;
            }

            th {
              border-left: 1px solid #dedede;
            }

            th:first-child {
              border-left: 0;
            }

            td:first-child {
              border-left: 0;
            }

            td:last-child {
              border-right: 0;
            }

            tr:last-child {
              td {
                border-bottom: 0;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
