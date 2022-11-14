import axios from "axios";
import { useEffect } from "react";
import { useState } from "react";
import { Ellipsis } from "./Ellipsis";
import RangeWithIcons from "../../mypage/review/RangeWithIcons";
import Pagination from "../Pagination";

export default function Review({ productId }) {
  const [reviews, setReviews] = useState([]);
  const [page, setPage] = useState(1);
  const [lastPage, setLastPage] = useState();

  const getReview = async () => {
    if (productId) {
      await axios
        .get(`/api/reviews?product=${productId}&page=${page}`)
        .then((res) => {
          setReviews(res.data.data);
          setPage(res.data.pageInfo.currentPage);
          setLastPage(res.data.pageInfo.lastPage);
        });
    }
  };

  useEffect(() => {
    getReview();
  }, [page]);

  return (
    <>
      {reviews.length > 0 ? (
        <div className="tableWrapper">
          <table>
            <colgroup>
              <col style={{ width: "15%" }} />
              <col style={{ width: "45%" }} />
              <col style={{ width: "15%" }} />
              <col style={{ width: "25%" }} />
            </colgroup>
            <tbody>
              {reviews.length > 0 &&
                reviews.map((review) => {
                  return (
                    <tr key={review.id} style={{ height: "96px" }}>
                      <td>
                        <RangeWithIcons
                          borderColor={"#ffd151"}
                          value={review.rating}
                          disabled={true}
                          readOnly={true}
                          starWidth={"15px"}
                        />
                      </td>
                      <td>
                        {/* <p className="reviewContents">{review.contents}</p> */}
                        <Ellipsis text={review.contents} />
                      </td>
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
      ) : (
        <div className="noReview">
          <p>아직 작성된 후기가 없습니다.</p>
        </div>
      )}

      <style jsx>{`
        .noReview {
          text-align: center;
          margin: 2rem 0 3rem 0;
        }

        .tableWrapper {
          table {
            table-layout: fixed;
            width: 100%;
            border-collapse: collapse;
            text-align: center;

            tr {
              border-top: 1px solid #dfdfdf;
              border-bottom: 1px solid #dfdfdf;
              td {
                height: 100%;

                .reviewContents {
                  overflow: auto;
                  width: 100%;
                  word-wrap: break-word;
                  text-align: left;
                }
              }
            }
          }
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */

          .tableWrapper {
            width: 1120px;
            margin: 0 auto 150px;
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */

          .tableWrapper {
            width: 85vw;
            margin: 0 auto 5vw;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .tableWrapper {
            width: 450px;
            height: 650px;
            margin: 0 auto 50px;
          }
        }
      `}</style>
    </>
  );
}
