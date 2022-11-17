export default function NoSearch() {
  return (
    <>
      <div className="NotFoundWrapper">
        <div className="image">
          <img src="/404.png" alt="" />
        </div>
        <div className="comment">
          <span>까까에는 이런 상품이 없어요 ㅠㅠ</span>
          <span>다른 키워드로 검색해주세요!</span>
        </div>

        <style jsx>{`
          .NotFoundWrapper {
            display: flex;
            flex-direction: column;
            justify-content: center;

            .image {
              display: flex;
              justify-content: center;
            }

            .comment {
              display: flex;
              flex-direction: column;
              justify-content: center;
              text-align: center;
            }
          }
          @media screen and (min-width: 769px) {
            /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
            .NotFoundWrapper {
              .image {
                img {
                  width: 200px;
                }
              }

              .comment {
                font-size: 30px;
              }
            }
          }

          @media screen and (max-width: 768px) {
            /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              .image {
                img {
                  width: 120px;
                }
              }

              .comment {
                font-size: 15px;
              }
            }
          }

          @media screen and (max-width: 480px) {
            /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
            .NotFoundWrapper {
              .image {
                img {
                  width: 150px;
                }
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
