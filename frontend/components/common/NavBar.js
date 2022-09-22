import Link from "next/link";

export default function NavBar() {
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
            <Link href="/product/productList">
              <a>전체</a>
            </Link>
          </li>
          <li>
            <Link href="/product/productList">
              <a>비스킷/샌드</a>
            </Link>
          </li>
          <li>
            <Link href="/product/productList">
              <a>스낵/봉지과자</a>
            </Link>
          </li>
          <li>
            <Link href="/product/productList">
              <a>캔디/사탕/젤리</a>
            </Link>
          </li>
          <li>
            <Link href="/product/productList">
              <a>초콜릿</a>
            </Link>
          </li>
          <li>
            <Link href="/product/productList">
              <a>껌/자일리톨</a>
            </Link>
          </li>
        </ul>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            max-width: 1000px;
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
