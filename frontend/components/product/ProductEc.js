import Link from "next/link";

export default function ProductEc({ imgsrc, link, context }) {
  return (
    <>
      <div className="btn">
        <Link href="/">
          <a>
            <div className="pab">
              <div className="front">
                <img src="/sample.png" />
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
          width: 10vw;
          max-height: 192px;
          height: 10vw;
          text-align: center;
          margin: 0 auto;
          border: 1px solid;
          border-radius: 100%;

          a {
            position: absolute;
            height: 10vw;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%); /* 자식요소에 translate 값 주기*/
          }

          img {
            max-height: 192px;
            height: 10vw;
          }

          .pab {
            position: relative;
            width: 10vw;
            height: 100px;

            .front {
              position: absolute;
              width: 100%;
              height: 10vw;
            }
            .back {
              position: absolute;
              opacity: 0;
              width: 100%;
              height: 10vw;
              left: 50%;
              top: 100%;
              border-radius: 100%;
              transform: translate(
                -50%,
                -52%
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
