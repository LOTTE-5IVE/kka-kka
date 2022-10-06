import Link from "next/link";
import { useRouter } from "next/router";
import { useState } from "react";

export default function AdminSidebar() {
  const [tab, setTab] = useState("order");

  const router = useRouter();
  const category = (cat_id) => {
    router.push({
      pathname: `/product`,
      query: {
        cat_id,
      },
    });
  };

  return (
    <>
      <div className="contents">
        <div className="logo">
          <img width="96px" src="/sample.png" />
        </div>
        <div className="title">
          <h2>프로모션</h2>
        </div>
        <ul>
          <li onClick={() => setTab("0")}>
            <Link href="/product?cat_id=1">
              <a className={`${tab === "0" ? "active" : ""}`}>혜택 등록</a>
            </Link>
          </li>
          <li onClick={() => setTab("2")}>
            <div onClick={() => category(2)} className="category">
              <a className={`${tab === "2" ? "active" : ""}`}>혜택 조회/수정</a>
            </div>
          </li>
        </ul>

        <div className="title">
          <h2>상품관리</h2>
        </div>
        <ul>
          <li onClick={() => setTab("0")}>
            <Link href="/product?cat_id=1">
              <a className={`${tab === "0" ? "active" : ""}`}>상품 등록</a>
            </Link>
          </li>
          <li onClick={() => setTab("2")}>
            <div onClick={() => category(2)} className="category">
              <a className={`${tab === "2" ? "active" : ""}`}>상품 조회/수정</a>
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        .contents {
          width: 100%;

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

            color: #dedede;

            a {
              cursor: pointer;
            }

            .active {
              color: #3a3a3a;
            }
          }
        }
      `}</style>
    </>
  );
}
