import Image from "next/image";
import Title from "../components/common/Title";

export default function NotFound() {
  return (
    <>
      <Title title="Not Found" />
      <div className="NotFoundWrapper">
        <div className="image">
          {/* <img src="/404.png" /> */}
          <Image src="/404.png" alt="img" layout="fill" />
        </div>
        <div className="comment">
          <span>까까에는 이런 페이지가 없어요 ㅠㅠ</span>
          <span>This page could not be found.</span>
        </div>

        <style jsx>{`
          .NotFoundWrapper {
            width: 100%;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;

            .image {
              margin: 0 auto;
              display: flex;
              justify-content: center;
              position: relative;
              width: 15%;
            }

            .image:after {
              content: "";
              display: block;
              padding-bottom: 100%;
            }

            .comment {
              display: flex;
              flex-direction: column;
              justify-content: center;
              text-align: center;
              font-size: 30px;
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              .image {
                width: 30%;
              }

              .comment {
                font-size: 20px;
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              .image {
                width: 30%;
              }

              .comment {
                font-size: 15px;
              }
            }
          }
        `}</style>
      </div>
    </>
  );
}

NotFound.displayName = "NotFound";
