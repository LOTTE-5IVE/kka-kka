import Link from "next/link";

export default function ProductRec({ id, imgsrc }) {
  return (
    <>
      <div className="wrapper">
        <Link href={`/product/productDetail?id=${id}`}>
          <a>
            <div className="imageSection">
              <img src={imgsrc} />
            </div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .wrapper {
            .imageSection {
              text-align: center;
              border-radius: 20px;
              background-color: #f5f5f5;

              img {
                height: 300px;
              }
            }
          }
        }

        @media screen and (max-width: 768px) {
          /* 태블릿에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            .imageSection {
              text-align: center;
              border-radius: 1.05vw;
              background-color: #f5f5f5;

              img {
                height: 15.78vw;
              }
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .wrapper {
            .imageSection {
              text-align: center;
              border-radius: 20px;
              background-color: #f5f5f5;

              img {
                height: 150px;
              }
            }
          }
        }
      `}</style>
    </>
  );
}

ProductRec.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
};
