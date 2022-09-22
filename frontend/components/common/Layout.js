import Footer from "./FooterComp";
import Header from "./HeaderComp";
import NavBar from "./NavBar";

export default function Layout({ children }) {
  return (
    <>
      <div className="top">
        <Header />
        <NavBar />
      </div>

      <div className="content">{children}</div>
      <Footer />

      <style jsx>
        {`
          .top {
            min-height: 100px;
            position: fixed;
            margin: 0 auto;
            left: 0;
            right: 0;
            background-color: #fff;
            border-bottom: 1px solid #d8d8d8;
            z-index: 999;
          }

          .content {
            padding-top: 55px;
            min-height: 850px;
          }
        `}
      </style>
    </>
  );
}
