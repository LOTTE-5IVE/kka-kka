import Link from "next/link";

export default function ProductEc({ imgsrc, link, context }) {
  return (
    <>
      <div className="btn">
        <Link href="/">
          <a>
            <div className="pab">
              <div className="front">
                <img src="/sample.png" alt="" />
              </div>
              <div className="back">
                <p className="bottom_title">test</p>
                <p className="bottom_detail">test_detail</p>
              </div>
            </div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        .btn {
          position: relative;
          width: 13vw;
          max-height: 400px;
          height: 13vw;
          text-align: center;
          margin: 0 auto;
          background-color: #f5f5f5;
          border-radius: 100%;
          overflow: hidden;

          a {
            position: absolute;
            height: 13vw;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%); /* 자식요소에 translate 값 주기*/
          }

          img {
            max-height: 500px;
            height: 100%;
          }

          .pab {
            position: relative;
            width: 13vw;
            max-height: 400px;
            height: 13vw;
            display: flex;
            justify-content: center;
            align-items: center;

            .front {
              position: absolute;
              width: 100%;
              height: 13vw;
            }
            .back {
              position: absolute;
              opacity: 0;
              width: 13vw;
              max-height: 400px;
              height: 13vw;
              left: 50%;
              top: 50%;
              border-radius: 100%;
              transform: translate(
                -50%,
                -50%
              ); /* 자식요소에 translate 값 주기*/
            }

            .back:hover {
              background: #000;
              opacity: 0.7;
              color: #fff;
              transition: all 0.5s ease-out;
              cursor: pointer;
            }
          }
        }
      `}</style>
    </>
  );
}
