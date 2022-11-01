import Title from "../components/common/Title";

export default function NotFound() {
  return (
    <>
      <Title title="Not Found" />
      <div className="NotFoundWrapper">
        <div className="image">
          <img src="/404.png" />
        </div>
        <div className="comment">
          <span>까까에는 이런 페이지가 없어요 ㅠㅠ</span>
          <span>This page could not be found.</span>
        </div>

        <style jsx>{`
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .NotFoundWrapper {
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);

              .image {
                display: flex;
                justify-content: center;

                img {
                  width: 200px;
                }
              }

              .comment {
                display: flex;
                flex-direction: column;
                justify-content: center;
                text-align: center;
                font-size: 30px;
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              width: 100vw;
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);

              .image {
                display: flex;
                justify-content: center;

                img {
                  width: 200px;
                }
              }

              .comment {
                display: flex;
                flex-direction: column;
                justify-content: center;
                text-align: center;
                font-size: 20px;
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              width: 100vw;
              position: absolute;
              top: 50%;
              left: 50%;
              transform: translate(-50%, -50%);

              .image {
                display: flex;
                justify-content: center;

                img {
                  width: 150px;
                }
              }

              .comment {
                display: flex;
                flex-direction: column;
                justify-content: center;
                text-align: center;
                font-size: 15px;
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}
