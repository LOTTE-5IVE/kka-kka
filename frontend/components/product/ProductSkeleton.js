export default function ProductSkeleton() {
  return (
    <div className="skeletonWrapper">
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>
      <div className="skeleton">
        <div className="skeleton__title" />
        <div className="skeleton__text" />
        <div className="skeleton__text" />
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .skeletonWrapper {
            display: table;
            width: 1035px;
            height: 1566px;

            .skeleton {
              display: inline-block;
              width: 33.3%;
              margin-bottom: 50px;
            }
          }

          .skeleton {
            background-color: #ffffff;
            border-radius: 12px;
            box-sizing: border-box;
            min-height: 200px;
            margin-top: 20px;
            padding: 30px;
          }

          .skeleton__title {
            background-color: #e7edf1;
            border-radius: 12px;
            width: 300px;
            height: 300px;
            margin-bottom: 24px;
          }

          .skeleton__text {
            background-color: #e7edf1;
            border-radius: 8px;
            width: 300px;
            height: 24px;
            margin-top: 20px;
          }

          .skeleton__title,
          .skeleton__text {
            animation: shimmer 1.2s infinite linear;
            background-image: linear-gradient(
              90deg,
              #e7edf1 0px,
              #f1f6fa 200px,
              #e7edf1 400px
            );
            background-size: 1200px;
          }

          @keyframes shimmer {
            0% {
              background-position: -400px;
            }
            50% {
              background-position: 700px;
            }
            100% {
              background-position: 700px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .skeletonWrapper {
            display: table;
            width: 60vw;
            height: 82.42vw;
            padding-left: 40px;

            .skeleton {
              display: inline-block;
              width: 33.3%;
              margin-bottom: 3vw;
            }
          }

          .skeleton {
            background-color: #ffffff;
            border-radius: 12px;
            box-sizing: border-box;
            min-height: 100px;
            margin-top: 10px;
          }

          .skeleton__title {
            background-color: #e7edf1;
            border-radius: 12px;
            width: 15.68vw;
            height: 15.68vw;
            margin: 0 auto 15px;
          }

          .skeleton__text {
            background-color: #e7edf1;
            border-radius: 8px;
            width: 15.68vw;
            height: 24px;
            margin: 5px auto 0px;
          }

          .skeleton__title,
          .skeleton__text {
            animation: shimmer 1.2s infinite linear;
            background-image: linear-gradient(
              90deg,
              #e7edf1 0px,
              #f1f6fa 200px,
              #e7edf1 400px
            );
            background-size: 1200px;
          }

          @keyframes shimmer {
            0% {
              background-position: -400px;
            }
            50% {
              background-position: 700px;
            }
            100% {
              background-position: 700px;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .skeletonWrapper {
            display: table;
            width: 480px;
            height: 750px;
            padding: 0;

            .skeleton {
              display: inline-block;
              width: 33.3%;
              margin-bottom: 10px;
            }
          }

          .skeleton {
            background-color: #ffffff;
            border-radius: 12px;
            box-sizing: border-box;
            min-height: 200px;
            margin-top: 10px;
          }

          .skeleton__title {
            background-color: #e7edf1;
            border-radius: 12px;
            width: 150px;
            height: 150px;
            margin-bottom: 12px;
          }

          .skeleton__text {
            background-color: #e7edf1;
            border-radius: 8px;
            width: 150px;
            height: 24px;
            margin-top: 10px;
          }

          .skeleton__title,
          .skeleton__text {
            animation: shimmer 1.2s infinite linear;
            background-image: linear-gradient(
              90deg,
              #e7edf1 0px,
              #f1f6fa 200px,
              #e7edf1 400px
            );
            background-size: 1200px;
          }

          @keyframes shimmer {
            0% {
              background-position: -400px;
            }
            50% {
              background-position: 700px;
            }
            100% {
              background-position: 700px;
            }
          }
        }
      `}</style>
    </div>
  );
}
