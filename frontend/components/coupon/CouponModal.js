export function CouponModal({ children }) {
  return (
    <>
      <div className="ModalBg"></div>
      <div className="ModalOverlay">
        <div className="wrapper">
          <div className="container">{children}</div>
        </div>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .ModalBg {
            position: fixed;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 1903px;
            height: 929px;
            background-color: rgba(0, 0, 0, 0.3);
          }
          .ModalOverlay {
            position: absolute;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 1903px;
            height: 929px;

            .wrapper {
              position: absolute;
              top: 465px;
              left: 951px;
              transform: translate(-50%, -50%);

              .container {
                margin: 40px auto 0;
                background-color: white;
                width: 700px;

                border: 1px solid #dedede;
                box-shadow: 4px 5px 3px #595959;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .ModalBg {
            position: fixed;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.3);
          }

          .ModalOverlay {
            position: absolute;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 100vw;
            height: 100%;

            .wrapper {
              position: absolute;
              top: 45%;
              left: 50vw;
              transform: translate(-50%, -50%);

              .container {
                margin: 10vw auto 0;
                background-color: white;
                width: 80vw;
                border: 1px solid #dedede;
                box-shadow: 4px 5px 3px #595959;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .ModalBg {
            position: fixed;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 480px;
            height: 1100px;
            background-color: rgba(0, 0, 0, 0.3);
          }

          .ModalOverlay {
            position: absolute;
            z-index: 9999;
            top: 0;
            left: 0;
            width: 480px;
            height: 1100px;

            .wrapper {
              position: absolute;
              top: 350px;
              left: 240px;
              transform: translate(-50%, -50%);

              .container {
                margin: 40px auto 0;
                background-color: white;
                width: 400px;

                border: 1px solid #dedede;
                box-shadow: 4px 5px 3px #595959;
              }
            }
          }
        }
      `}</style>
    </>
  );
}
