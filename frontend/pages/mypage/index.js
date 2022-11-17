import { useState } from "react";
import Title from "../../components/common/Title";
import MyCoupon from "../../components/mypage/MyCoupon";
import MyInfoCard from "../../components/mypage/MyInfoCard";
import Mysidebar from "../../components/mypage/Mysidebar";
import MyOrder from "../../components/mypage/MyOrder";
import MyInfoEdit from "../../components/mypage/MyInfoEdit";
import MyOrderDetail from "../../components/mypage/MyOrderDetail";

export default function MyPage() {
  const [tab, setTab] = useState("order");
  const [detail, setDetail] = useState(false);
  const [orderDetail, setOrderDetail] = useState();
  const [reviewed, setReviewed] = useState(false);
  const [orderLoaded, setOrderLoaded] = useState(false);

  function handleTab(menu) {
    setTab(menu);
  }

  function handleDetail(bool) {
    setDetail(bool);
  }

  return (
    <>
      <Title title="마이페이지" />
      <div>
        <div className="MyPageLContents">
          <div className="sidebar">
            <Mysidebar handleTab={handleTab} handleDetail={handleDetail} />
          </div>
          <div className="wrapper">
            {!detail && (
              <div>
                <MyInfoCard />
              </div>
            )}

            <div className="mypageMenu">
              {tab == "info" ? (
                <MyInfoEdit />
              ) : tab == "coupon" ? (
                <MyCoupon />
              ) : tab == "order" && !detail ? (
                <MyOrder
                  handleDetail={handleDetail}
                  setOrderDetail={setOrderDetail}
                />
              ) : (
                <MyOrderDetail
                    orderDetail={orderDetail}
                    handleDetail={handleDetail}
                />
              )}
            </div>
          </div>
        </div>
        <style jsx>{`
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .MyPageLContents {
              margin: 0 auto;
              width: 1332px;
              display: flex;

              .sidebar {
                display: inline-block;
                width: 240px;
                margin-right: 93px;
              }

              .wrapper {
                width: 970px;
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .MyPageLContents {
              margin: 0 auto;
              width: 100vw;
              display: flex;

              .sidebar {
                display: inline-block;
                width: 25vw;
                margin-right: 5vw;
              }

              .wrapper {
                width: 70vw;
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .MyPageLContents {
              margin: 0 auto;
              width: 480px;
              display: flex;

              .sidebar {
                display: inline-block;
                width: 100px;
                margin-right: 20px;
              }

              .wrapper {
                width: 340px;
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
