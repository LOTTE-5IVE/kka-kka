import { useState } from "react";
import { ThemeGray } from "../../typings/ThemeColor";

export default function Pagination({ page, setPage, lastPage }) {
  const [start, setStart] = useState(1);
  const [end, setEnd] = useState(10);

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
            <img src="/btn_page_prev.gif" />
          </li>

          {lastPage &&
            Array(lastPage + 1)
              .fill()
              .slice(start, end + 1)
              .map((_, i) => (
                <li
                  className={page == start + i ? "active" : "pageIndex"}
                  width="15px"
                  key={i + 1}
                  onClick={() => {
                    setPage(start + i);
                  }}
                  style={{ margin: "0 10px" }}
                >
                  {start + i}
                </li>
              ))}
          <li
            onClick={() => {
              console.log("next click");
              if (page == end && page < lastPage) {
                console.log("next click2");
                setStart(start + 10);
                setEnd(end + 10);
              }
              if (page < lastPage) {
                setPage(page + 1);
              }
            }}
            disabled={page === lastPage - 200}
          >
            <img src="/btn_page_next.gif" />
          </li>
        </ul>
      </div>
      <style jsx>{`
        .wrapper {
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
            }

            .pageIndex {
              border-bottom: 0;
              padding: 0px;
              font-weight: 400;
              width: 32px;
              height: 32px;
              border-radius: 50%;
              line-height: 34px;
              text-align: center;
              transition: 0.5s;
            }

            .pageIndex:hover {
              background: ${ThemeGray};
            }

            .active {
              border-bottom: 0;
              padding: 0px;
              font-weight: 400;
              color: #fff;
              width: 32px;
              height: 32px;
              border-radius: 50%;
              background: #ff3d44;
              line-height: 34px;
              text-align: center;
            }
          }
        }
      `}</style>
    </div>
  );
}
