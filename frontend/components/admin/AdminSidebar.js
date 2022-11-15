import { useState } from "react";

export default function AdminSidebar({ LmenuHandler, SmenuHandler }) {
  const [tab, setTab] = useState("혜택 등록");
  const [Smenu, setSmenu] = useState("혜택 등록");

  return (
    <>
      <div className="contents">
        <div className="logo">
          <img width="96px" src="/main/logo.png" alt="" />
        </div>
        <div className="title">
          <h2>프로모션</h2>
        </div>
        <ul>
          <li
            onClick={() => {
              setSmenu("혜택 등록");
              LmenuHandler("프로모션");
              SmenuHandler("혜택 등록");
            }}
          >
            <div className="category">
              <a className={`${Smenu === "혜택 등록" ? "active" : ""}`}>
                혜택 등록
              </a>
            </div>
          </li>
          <li
            onClick={() => {
              setSmenu("혜택 조회/수정");
              LmenuHandler("프로모션");
              SmenuHandler("혜택 조회/수정");
            }}
          >
            <div className="category">
              <a className={`${Smenu === "혜택 조회/수정" ? "active" : ""}`}>
                혜택 조회/수정
              </a>
            </div>
          </li>
        </ul>

        <div className="title">
          <h2>상품관리</h2>
        </div>
        <ul>
          {/* <li
            onClick={() => {
              setSmenu("상품 등록");
              LmenuHandler("상품관리");
              SmenuHandler("상품 등록");
            }}
          >
            <div className="category">
              <a className={`${Smenu === "상품 등록" ? "active" : ""}`}>
                상품 등록
              </a>
            </div>
          </li> */}
          <li
            onClick={() => {
              setSmenu("상품 조회/수정");
              LmenuHandler("상품관리");
              SmenuHandler("상품 조회/수정");
            }}
          >
            <div className="category">
              <a className={`${Smenu === "상품 조회/수정" ? "active" : ""}`}>
                상품 조회/수정
              </a>
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        .contents {
          margin: 0 auto;
          width: 70%;

          .title {
            line-height: 1vw;
          }

          ul {
            padding: 0;
            list-style: none;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            /* align-items: center; */
          }

          li {
            font-size: 17px;
            font-weight: 700;
            line-height: 40px;
            padding: 0;

            color: #3a3a3a;

            a {
              cursor: pointer;
            }

            .active {
              color: #fe5c57;
            }
          }
        }
      `}</style>
    </>
  );
}
