import Link from "next/link";
import { useRouter } from "next/router";

export default function NavBar() {
  const router = useRouter();
  const category = (cat_id) => {
    router.push(`/product?cat_id=${cat_id}`);
  };

  return (
    <nav>
      <div className="NavBarWrapper">
        <ul>
          <li>
            <Link href="/">
              <a>홈</a>
            </Link>
          </li>
          <li>
            <Link
              href={{
                pathname: "/product",
                query: {
                  cat_id: 0,
                },
              }}
            >
              <a>전체</a>
            </Link>
          </li>
          <li>
            <Link
              href={{
                pathname: "/product",
                query: {
                  cat_id: 1,
                },
              }}
            >
              <a>비스킷/샌드</a>
            </Link>
          </li>
          <li>
            <div onClick={() => category(2)} className="category">
              <a>스낵/봉지과자</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(3)} className="category">
              <a>박스과자</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(4)} className="category">
              <a>캔디/사탕/젤리</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(5)} className="category">
              <a>시리얼/바</a>
            </div>
          </li>

          <li>
            <div onClick={() => category(6)} className="category">
              <a>초콜릿</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(7)} className="category">
              <a>껌/자일리톨</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(8)} className="category">
              <a>선물세트</a>
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        .NavBarWrapper {
          margin: 0 auto;
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        ul {
          list-style: none;
          margin: 0 auto;
          display: flex;
          justify-content: space-between;
          align-items: center;
        }

        li {
          font-weight: 600;

          a {
            cursor: pointer;
          }
        }
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .NavBarWrapper {
            width: 1300px;
            height: 55px;
          }

          ul {
            width: 1040px;
            height: 55px;
          }

          li {
            font-size: 17px;
            line-height: 55px;

            a:hover:after {
              transform: scaleX(1);
            }
            a:after {
              display: block;
              content: "";
              border-bottom: solid 3px #ea2129;
              transform: scaleX(0);
              transition: transform 250ms ease-in-out;
              transform-origin: 0% 50%;
            }
          }
        }

        @media screen and (max-width: 768px) {
          .NavBarWrapper {
            width: 75vw;
            height: 6vw;
          }

          ul {
            width: 75vw;
            height: 6vw;
            padding: 0;
            flex-wrap: wrap;
          }

          li {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 15vw;
            font-size: 0.89vw;
            line-height: 2.89vw;
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .NavBarWrapper {
            width: 480px;
            height: 60px;
          }

          ul {
            width: 400px;
            height: 60px;
            padding: 0;
            flex-wrap: wrap;
          }

          li {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 80px;
            height: 30px;
            font-size: 10px;
            line-height: 20px;
          }
        }
      `}</style>
    </nav>
  );
}
