import Link from "next/link";
import { useRouter } from "next/router";

export default function NavBar() {
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
    <nav>
      <div className="wrapper">
        <ul>
          <li>
            <Link href="/">
              <a>홈</a>
            </Link>
          </li>
          <li>
            <Link href="/product?cat_id=1">
              <a>전체</a>
            </Link>
          </li>
          <li>
            <div onClick={() => category(2)} className="category">
              <a>비스킷/샌드</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(3)} className="category">
              <a>스낵/봉지과자</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(4)} className="category">
              <a>박스과자</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(5)} className="category">
              <a>시리얼/바</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(6)} className="category">
              <a>캔디/사탕/젤리</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(7)} className="category">
              <a>초콜릿</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(8)} className="category">
              <a>껌/자일리톨</a>
            </div>
          </li>
          <li>
            <div onClick={() => category(9)} className="category">
              <a>선물세트</a>
            </div>
          </li>
        </ul>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            max-width: 1300px;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
          }

          ul {
            list-style: none;
            width: 80%;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
          }

          li {
            font-size: 17px;
            font-weight: 600;
            line-height: 55px;

            a {
              cursor: pointer;
            }
          }
        }

        @media screen and (max-width: 900px) {
          .search {
            display: none;
          }
        }

        @media screen and (max-width: 768px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */

          ul {
            list-style: none;
            width: 80%;
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
          }
        }
      `}</style>
    </nav>
  );
}
