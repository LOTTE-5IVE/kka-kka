import Link from "next/link";

export default function Header() {
  return (
    <div>
      <div className="wrapper">
        <div className="logo">
          <Link href="/">
            <img height="52" src="/vercel.svg" />
          </Link>
        </div>
        <div className="search">
          <form>
            <input
              type="text"
              size="30"
              placeholder="검색어를 입력해주세요"
            ></input>
            <input
              type="image"
              src="/common/main_search.png"
              alt="검색"
              onClick=""
            />
          </form>
        </div>
        <div className="icons">
          <div className="top">
            <Link href="/member/join">
              <a>회원가입</a>
            </Link>

            <Link href="/member/login">
              <a>로그인</a>
            </Link>
          </div>
          <div className="bottom">
            <Link href="/">
              <img src="/common/top_mypage.png" />
            </Link>

            <Link href="/">
              <img src="/common/top_cart.png" />
            </Link>
          </div>
        </div>
        <style jsx>{`
          .wrapper {
            max-height: 120px;
            height: 15vh;
          }
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .wrapper {
              max-width: 1280px;
              margin: 0 auto;
              display: flex;
              justify-content: space-between;
              align-items: center;
            }

            .search {
              margin-left: -35%;
              border: 2px solid #ed1b23;
              border-radius: 40px;
              padding: 0 17px;

              input[type="text"] {
                border: none;
                border-radius: 40px;
                width: 317px;
                height: 45px;
                line-height: 45px;
                padding: 0;
                box-sizing: border-box;
                color: #c5c9cd;
                font-size: 1em;
                font-weight: 600;
              }

              input[type="text"]:focus {
                outline: none;
                color: #000;
              }

              input[type="image"] {
                width: 24px;
                height: 24px;
                position: relative;
                top: 5px;
              }
            }

            .icons {
              height: 80px;
              width: 100px;
              margin: auto 0;

              .top {
                line-height: 40px;
                font-weight: 700;
                font-size: 12px;
                display: flex;
                justify-content: space-between;
                position: relative;
                top: -15px;
              }

              .bottom {
                display: flex;
                justify-content: space-around;
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
          }
        `}</style>
      </div>
    </div>
  );
}
