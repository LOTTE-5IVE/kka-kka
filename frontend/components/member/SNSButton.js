import Link from "next/link";

export default function SNSButton({ imgsrc, link, context }) {
  return (
    <>
      <div className="btn">
        <Link href={link}>
          <a>
            <div>
              <img src={imgsrc} />
            </div>
            <div className="context">{context}</div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        @media screen and (min-width: 769px) {
          /* 데스크탑에서 사용될 스타일을 여기에 작성합니다. */
          .btn {
            width: 100px;
            height: 100px;
            text-align: center;
            margin: 0 auto;

            a {
              .context {
                margin-top: 30%;
                font-size: 16px;
              }
            }

            img {
              height: 96px;
            }
          }
        }

        @media screen and (max-width: 768px) {
          .btn {
            width: 50px;
            height: 50px;
            text-align: center;
            margin: 0 auto;

            a {
              .context {
                margin-top: 30%;
                font-size: 8px;
              }
            }

            img {
              height: 48px;
            }
          }
        }

        @media screen and (max-width: 480px) {
          /* 모바일에 사용될 스트일 시트를 여기에 작성합니다. */
          .btn {
            width: 100px;
            height: 100px;
            text-align: center;
            margin: 0 auto;

            a {
              .context {
                margin-top: 30%;
                font-size: 16px;
              }
            }

            img {
              height: 96px;
            }
          }
        }
      `}</style>
    </>
  );
}
