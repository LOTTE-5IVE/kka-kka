import RangeWithIcons from "../../mypage/review/RangeWithIcons";
import Pagination from "../Pagination";

export default function Review({ reviews, page, setPage, lastPage }) {
  return (
    <>
      <div className="tableWrapper">
        <table>
          <colgroup>
            <col style={{ width: "15%" }} />
            <col style={{ width: "45%" }} />
            <col style={{ width: "15%" }} />
            <col style={{ width: "30%" }} />
          </colgroup>
          <tbody>
            {reviews.length > 0 &&
              reviews.map((review, index) => {
                return (
                  <tr key={index} style={{ height: "96px" }}>
                    <td>
                      <RangeWithIcons
                        borderColor={"#ffd151"}
                        value={review.rating}
                        disabled={true}
                        readOnly={true}
                        starWidth={"15px"}
                      />
                    </td>
                    <td>{review.contents}</td>
                    <td>{review.member.name}</td>
                    <td>{review.createdAt}</td>
                  </tr>
                );
              })}
          </tbody>
        </table>
        <div className="pagination">
          <Pagination page={page} setPage={setPage} lastPage={lastPage} />
        </div>
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .tableWrapper {
            width: 1120px;
            margin: 0 auto 150px;

            table {
              width: 100%;
              border-collapse: collapse;

              tr {
                border-top: 1px solid #dfdfdf;
                border-bottom: 1px solid #dfdfdf;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .tableWrapper {
            width: 1120px;
            margin: 0 auto 150px;

            table {
              width: 100%;
              border-collapse: collapse;

              tr {
                border-top: 1px solid #dfdfdf;
                border-bottom: 1px solid #dfdfdf;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .tableWrapper {
            width: 450px;
            height: 650px;
            margin: 0 auto 50px;

            table {
              width: 100%;
              border-collapse: collapse;

              tr {
                border-top: 1px solid #dfdfdf;
                border-bottom: 1px solid #dfdfdf;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
