export default function Info({ detailImageUrl }) {
  return (
    <>
      <div>
        <img src={detailImageUrl} />
      </div>
      <style jsx>{`
        div {
          margin: auto;
        }

        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          div {
            img {
              width: 1240px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          div {
            img {
              width: 85vw;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          div {
            height: 500px;
            overflow: auto;
            img {
              width: 450px;
            }
          }
        }
      `}</style>
    </>
  );
}
