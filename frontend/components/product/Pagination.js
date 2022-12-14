import { useEffect } from "react";
import { useState } from "react";
import { ThemeGray } from "../../typings/ThemeColor";

export default function Pagination({ page, setPage, lastPage }) {
  const [start, setStart] = useState(1);
  const [end, setEnd] = useState(10);

  useEffect(() => {
    setStart(1);
    setEnd(10);
  }, [lastPage]);

  return (
    <div className="wrapper">
      <div className="pageBtn">
        <ul>
          <li
            onClick={() => {
              if (page == start && page > 1) {
                setStart(start - 10);
                setEnd(end - 10);
              }

              if (page > 1) {
                setPage(page - 1);
              }
            }}
            disabled={page === 1}
          >
            <img className="btn" src="/common/btn_page_prev.gif" alt="" />
          </li>

          {lastPage &&
            Array(lastPage + 1)
              .fill()
              .slice(start, end + 1)
              .map((_, i) => (
                <li
                  className={
                    page == start + i ? "active pageIndex" : "normal pageIndex"
                  }
                  key={i + 1}
                  onClick={() => {
                    setPage(start + i);
                  }}
                >
                  {start + i}
                </li>
              ))}
          <li
            onClick={() => {
              if (page == end && page < lastPage) {
                setStart(start + 10);
                setEnd(end + 10);
              }
              if (page < lastPage) {
                setPage(page + 1);
              }
            }}
            disabled={page === lastPage - 200}
          >
            <img className="btn" src="/common/btn_page_next.gif" alt="" />
          </li>
        </ul>
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            width: 1035px;
            height: 132px;

            display: flex;
            justify-content: center;

            .pageBtn {
              display: flex;
              justify-content: center;
              font-size: 25px;
              margin-bottom: 50px;
              cursor: pointer;

              ul {
                list-style: none;
                display: flex;
                padding: 0;
              }

              li {
                display: flex;
                justify-content: center;
                align-items: center;

                .btn {
                  width: 32px;
                }
              }

              .pageIndex {
                width: 32px;
                height: 32px;
                margin: 0 10px;
                padding: 0px;
                border-bottom: 0;
                font-weight: 400;
                border-radius: 50%;
                line-height: 34px;
                text-align: center;
                transition: 0.5s;
              }

              .normal:hover {
                background: ${ThemeGray};
              }

              .active {
                color: #fff;
                background: #ff3d44;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            width: 61.5vw;
            height: 6vw;
            margin: 0;
            display: flex;
            justify-content: center;
            margin: 0 auto;

            .pageBtn {
              display: flex;
              justify-content: center;
              font-size: 1.32vw;
              margin-bottom: 0.5vw;
              cursor: pointer;

              ul {
                list-style: none;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
              }

              li {
                display: flex;
                justify-content: center;
                align-items: center;

                .btn {
                  width: 3vw;
                }
              }

              .pageIndex {
                width: 3vw;
                height: 3vw;
                margin: 0 0.52vw;
                padding: 0vw;
                border-bottom: 0vw;
                font-weight: 400;
                border-radius: 50%;
                line-height: 1.79vw;
                text-align: center;
                transition: 0.5s;
              }

              .normal:hover {
                background: ${ThemeGray};
              }

              .active {
                color: #fff;
                background: #ff3d44;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            width: 450px;
            height: 66px;
            display: flex;
            justify-content: center;

            .pageBtn {
              display: flex;
              justify-content: center;
              font-size: 12.5px;

              cursor: pointer;

              ul {
                list-style: none;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0;
              }

              li {
                .btn {
                  width: 16px;
                }
              }

              .pageIndex {
                width: 16px;
                height: 16px;
                margin: 0 5px;
                padding: 0px;
                border-bottom: 0;
                font-weight: 400;
                border-radius: 50%;
                line-height: 17px;
                text-align: center;
                transition: 0.5s;
              }

              .normal:hover {
                background: ${ThemeGray};
              }

              .active {
                color: #fff;
                background: #ff3d44;
              }
            }
          }
        }
      `}</style>
    </div>
  );
}
