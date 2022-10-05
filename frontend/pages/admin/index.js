import AdminSidebar from "../../components/admin/AdminSidebar";
import ButtonComp from "../../components/common/buttonComp";
import Title from "../../components/common/Title";

export default function admin() {
  return (
    <>
      <Title title="관리자" />
      <div className="wrapper">
        <div className="sidebar">
          <AdminSidebar />
        </div>
        <div className="promotions">
          <div className="title">
            <span>프로모션 > 혜택 등록</span>
          </div>
          <div className="contents">
            <table>
              <colgroup>
                <col style={{ width: "20%" }} />
                <col style={{ width: "80%" }} />
              </colgroup>

              <tbody>
                <tr>
                  <th scope="row">혜택 유형</th>
                  <td>
                    <div style={{ display: "flex" }}>
                      <div>할인</div>
                      <div>쿠폰</div>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th scope="row">혜택 이름</th>
                  <td style={{ display: "flex" }}>
                    <div>
                      <input
                        id="oname"
                        className="inputTypeText"
                        size="15"
                        type="text"
                      />
                    </div>

                    <div>
                      <span>수량</span>
                      <span>
                        x{" "}
                        <input
                          id="oname"
                          className="inputTypeText"
                          size="1"
                          type="text"
                        />
                      </span>
                    </div>
                  </td>
                </tr>
                <tr>
                  <th scope="row">할인 설정</th>
                  <td>
                    <input
                      id="oname"
                      className="inputTypeText"
                      size="2"
                      type="text"
                    />
                    % 최대{" "}
                    <input
                      id="oname"
                      className="inputTypeText"
                      size="10"
                      type="text"
                    />{" "}
                    원 *최대 금액만 설정시 정액 할인
                  </td>
                </tr>
                <tr>
                  <th scope="row">최소 주문 금액</th>
                  <td>
                    <input
                      id="oname"
                      className="inputTypeText"
                      placeholder=""
                      size="10"
                      defaultValue=""
                      type="text"
                    />
                    원
                  </td>
                </tr>
                <tr>
                  <th scope="row">다운로드 가능 기간</th>
                  <td>
                    <input id="oname" className="inputTypeText" type="date" /> ~{" "}
                    <input id="oname" className="inputTypeText" type="date" />
                  </td>
                </tr>
                <tr>
                  <th scope="row">유효 기간</th>
                  <td>
                    <input id="oname" className="inputTypeText" type="date" /> ~{" "}
                    <input id="oname" className="inputTypeText" type="date" />
                  </td>
                </tr>
                <tr>
                  <th scope="row">적용 대상 지정</th>
                  <td>
                    {/* <table>
                      <colgroup>
                        <col style={{ width: "20%" }} />
                        <col style={{ width: "40%" }} />
                        <col style={{ width: "40%" }} />
                      </colgroup>
                      <thead>
                        <th>카테고리</th>
                        <th>상품명</th>
                        <th>
                          <input type="checkbox" />
                        </th>
                      </thead>
                      <tbody>
                        <tr>
                          <td>혜택 이름</td>
                          <td>혜택 이름</td>
                          <td>혜택 이름</td>
                        </tr>
                      </tbody>
                    </table> */}
                  </td>
                </tr>
                <tr>
                  <td>
                    <ButtonComp context="혜택 등록하기" />
                  </td>
                  <td>
                    <ButtonComp context="취소" />
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <style jsx>{`
        .wrapper {
          display: flex;
          height: 100vh;

          .sidebar {
            display: inline-block;
            width: 15%;
            height: 100%;
            border-right: 1px solid;
          }

          .promotions {
            width: 100%;
            .title {
              width: 100%;
              border-bottom: 1px solid;
            }

            .contents {
              height: 100%;

              table {
                width: 90%;
                height: 90%;

                margin: auto;

                th {
                  text-align: left;
                }
              }
            }
          }
        }
      `}</style>
    </>
  );
}
