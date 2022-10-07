import Link from "next/link";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";
import NavBar from "../../components/common/NavBar";

export default function Mysidebar({ mypageCallback }) {
  const [tab, setTab] = useState("order");

  useEffect(() => {
    mypageCallback(tab);
  });

  return (
    <>
      <div className="contents">
        <div className="title">
          <h2>마이페이지</h2>
        </div>
        <ul>
          <li onClick={() => setTab("order")}>
            <a className={`${tab === "order" ? "active" : ""}`}>주문내역</a>
          </li>
          <li onClick={() => setTab("coupon")}>
            <a className={`${tab === "coupon" ? "active" : ""}`}>쿠폰함</a>
          </li>
        </ul>
      </div>

      <style jsx>{`
        .contents {
          width: 100%;

          .title {
            border-bottom: 1px solid;
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
