import Footer from "./FooterComp";
import Header from "./HeaderComp";
import NavBar from "./NavBar";

export default function Layout({ children }) {
  return (
    <>
      <div className="LayoutTop">
        <Header />
        <NavBar />
      </div>

      <div className="content">{children}</div>

      <div className="LayoutBottom">
        <Footer />
      </div>

      <style jsx>
        {`
          .LayoutTop {
            background-color: #fff;
            border-bottom: 1px solid #d8d8d8;
            z-index: 999;
          }

          @media screen and (min-width: 769px) {
            .LayoutTop {
              height: 177px;
              width: 1903px;
            }

            .content {
              width: 1903px;
              min-height: 850px;
            }

            .LayoutBottom {
              width: 1903px;
              height: 334px;
            }
          }

          @media screen and (max-width: 768px) {
            .LayoutTop {
              width: 100vw;
              height: 17vw;
            }

            .content {
              width: 100vw;
              min-height: 600px;
            }

            .LayoutBottom {
              width: 100vw;
              height: 25vw;
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .LayoutTop {
              width: 480px;
              height: 130px;
            }

            .content {
              width: 480px;
              min-height: 600px;
            }

            .LayoutBottom {
              width: 480px;
              height: 200px;
            }
          }
        `}
      </style>
    </>
  );
}
