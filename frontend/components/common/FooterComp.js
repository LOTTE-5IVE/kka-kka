import Link from "next/link";

export default function Footer() {
  return (
    <>
      <div className="footer">
        <div className="top-wrapper">
          <div className="top">
            <ul>
              <li>
                <Link href="/">
                  <a>팀소개</a>
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="bottom">
          <div className="up">
            Kka-Kka의 모든 콘텐츠는 저작권의 보호를 받고 있습니다.
          </div>
          <div className="down">
            <div>
              까까(주) | 주소 : 서울특별시 서초구 서초대로74길 33 비트교육센터 |
              대표이사: 최솔지
            </div>
            <div>
              공동대표: 신우주, 서지훈, 오명주, 김혜연 | 평일 : 10:00 - 17:00
              (주말, 공휴일 휴무)
            </div>
          </div>

          <div className="foot">
            &copy; Copyright KKA-KKA Co.,LTD. All rights reserved.
          </div>
        </div>
      </div>

      <style jsx>{`
        .footer {
          width: 100%;
          border: 0;
          background-color: #f8f8f8;
          font-size: 14px;
        }

        .top-wrapper {
          height: 5vw;
          width: 100%;
          border-bottom: 1px solid #e9e9e9;

          .top {
            height: 5vw;
            width: 75%;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            color: #9a9a9a;

            ul {
              padding: 0;
              display: flex;
              list-style: none;
              justify-content: center;
              align-items: center;
            }
          }
        }

        .bottom {
          width: 75%;
          margin: 0 auto;
          padding: 50px 0 50px;
          .up {
            color: #5a5a5a;
          }
          .down {
            color: #9a9a9a;
            margin: 22px 0 40px;
          }
        }

        .foot {
          color: #9a9a9a;
        }
      `}</style>
    </>
  );
}
