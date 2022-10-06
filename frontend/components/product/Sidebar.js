import Link from "next/link";
import { useRouter } from "next/router";
import NavBar from "../../components/common/NavBar";

export default function Sidebar() {
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
        <div className="title">
          <h2>과자모음</h2>
        </div>
        <ul>
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
            color: #3a3a3a;
          }
        }
      `}</style>
    </>
  );
}
