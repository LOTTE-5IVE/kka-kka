import Link from "next/link";
import { useRouter } from "next/router";
import { useEffect, useState } from "react";

export default function Sidebar({ menu }) {
  const [tab, setTab] = useState(menu);
  const router = useRouter();
  const category = (cat_id) => {
    router.push({
      pathname: `/product`,
      query: {
        cat_id,
      },
    });
  };

  useEffect(() => {
    setTab(menu);
  }, [menu]);

  return (
    <>
      <div className="contents">
        <div className="title">
          <p>과자모음</p>
        </div>
        <ul>
          <li onClick={() => setTab("0")}>
            <Link
              href={{
                pathname: "/product",
                query: {
                  cat_id: 0,
                },
              }}
            >
              <a className={`${tab === "0" ? "active" : ""}`}>전체</a>
            </Link>
          </li>
          <li onClick={() => setTab("1")}>
            <div onClick={() => category(1)} className="category">
              <a className={`${tab === "1" ? "active" : ""}`}>비스킷/샌드</a>
            </div>
          </li>
          <li onClick={() => setTab("2")}>
            <div onClick={() => category(2)} className="category">
              <a className={`${tab === "2" ? "active" : ""}`}>스낵/봉지과자</a>
            </div>
          </li>
          <li onClick={() => setTab("3")}>
            <div onClick={() => category(3)} className="category">
              <a className={`${tab === "3" ? "active" : ""}`}>박스과자</a>
            </div>
          </li>
          <li onClick={() => setTab("4")}>
            <div onClick={() => category(4)} className="category">
              <a className={`${tab === "4" ? "active" : ""}`}>캔디/사탕/젤리</a>
            </div>
          </li>
          <li onClick={() => setTab("5")}>
            <div onClick={() => category(5)} className="category">
              <a className={`${tab === "5" ? "active" : ""}`}>시리얼/바</a>
            </div>
          </li>

          <li onClick={() => setTab("6")}>
            <div onClick={() => category(6)} className="category">
              <a className={`${tab === "6" ? "active" : ""}`}>초콜릿</a>
            </div>
          </li>
          <li onClick={() => setTab("7")}>
            <div onClick={() => category(7)} className="category">
              <a className={`${tab === "7" ? "active" : ""}`}>껌/자일리톨</a>
            </div>
          </li>
          <li onClick={() => setTab("8")}>
            <div onClick={() => category(8)} className="category">
              <a className={`${tab === "8" ? "active" : ""}`}>선물세트</a>
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .contents {
            width: 100%;

            .title {
              border-bottom: 1px solid;
              line-height: 1vw;
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
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .contents {
            width: 100%;

            .title {
              border-bottom: 0.5px solid;
              line-height: 1vw;

              p {
                font-size: 12px;
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
              font-size: 8.5px;
              font-weight: 700;
              line-height: 20px;
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
