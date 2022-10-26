export default function Info({ detailImage_url }) {
  return (
    <>
      <div>
        <img src={detailImage_url} />
      </div>
      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          div {
            margin: auto;
            img {
              width: 1240px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          div {
            margin: auto;
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
