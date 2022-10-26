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

      <div className="content">
        <div className="empty"></div>
        {children}
      </div>

      <div className="LayoutBottom">
        <Footer />
      </div>
      <style jsx>
        {`
          @media screen and (min-width: 769px) {
            .LayoutTop {
              /* position: fixed; */
              position: absolute;
              height: 175px;
              width: 1903px;
              left: 0;
              right: 0;
              background-color: #fff;
              border-bottom: 1px solid #d8d8d8;
              z-index: 999;
            }

            .content {
              width: 1903px;
              min-height: 850px;

              .empty {
                width: 1903px;
                height: 175px;
              }
            }

            .LayoutBottom {
              width: 1903px;
              height: 334px;
            }
          }

          @media screen and (max-width: 768px) {
            .LayoutTop {
              /* position: fixed; */
              position: absolute;
              width: 951.5px;
              height: 87.5px;

              left: 0;
              right: 0;
              background-color: #fff;
              border-bottom: 1px solid #d8d8d8;
              z-index: 999;
            }

            .content {
              width: 951.5px;
              min-height: 600px;

              .empty {
                width: 951.5px;
                height: 87.5px;
              }
            }

            .LayoutBottom {
              width: 951.5px;
              height: 87.5px;
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .LayoutTop {
              /* position: fixed; */
              position: absolute;
              width: 480px;
              height: 130px;
              left: 0;
              right: 0;
              background-color: #fff;
              border-bottom: 1px solid #d8d8d8;
              z-index: 999;
            }

            .content {
              width: 480px;
              min-height: 600px;

              .empty {
                width: 480px;
                height: 130px;
              }
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
