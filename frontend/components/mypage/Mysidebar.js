import { useEffect, useState } from "react";

export default function Mysidebar({ handleTab }) {
  const [tab, setTab] = useState("order");

  useEffect(() => {
    handleTab(tab);
  }, [tab]);

  return (
    <>
      <div className="MysidebarContents">
        <div className="title">
          <p>마이페이지</p>
        </div>
        <ul>
          <li onClick={() => setTab("info")}>
            <a className={`${tab === "info" ? "active" : ""}`}>내 정보 수정</a>
          </li>
          <li onClick={() => setTab("order")}>
            <a className={`${tab === "order" ? "active" : ""}`}>주문내역</a>
          </li>
          <li onClick={() => setTab("coupon")}>
            <a className={`${tab === "coupon" ? "active" : ""}`}>쿠폰함</a>
          </li>
        </ul>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .MysidebarContents {
            width: 240px;

            .title {
              border-bottom: 1px solid;
              line-height: 19px;

              p {
                font-size: 24px;
                font-weight: 700;
              }
            }

            ul {
              padding: 0;
              list-style: none;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            }

            li {
              font-size: 17px;
              font-weight: 700;
              line-height: 40px;
              padding: 0;
              color: #dedede;
              a {
                cursor: pointer;
              }
              .active {
                color: #3a3a3a;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .MysidebarContents {
            width: 25vw;

            .title {
              border-bottom: 1px solid;
              line-height: 5vw;
            }

            ul {
              padding: 0;
              list-style: none;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            }

            li {
              font-size: 0.9vw;
              font-weight: 700;
              line-height: 5vw;
              padding: 0;
              color: #dedede;
              a {
                cursor: pointer;
              }
              .active {
                color: #3a3a3a;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .MysidebarContents {
            width: 100px;

            .title {
              border-bottom: 1px solid;
              line-height: 19px;

              p {
                font-size: 16px;
                font-weight: 700;
              }
            }

            ul {
              padding: 0;
              list-style: none;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
            }

            li {
              font-size: 12px;
              font-weight: 700;
              line-height: 25px;
              padding: 0;
              color: #dedede;
              a {
                cursor: pointer;
              }
              .active {
                color: #3a3a3a;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
