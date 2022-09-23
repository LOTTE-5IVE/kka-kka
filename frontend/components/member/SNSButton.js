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
            <div style={{ marginTop: "30%" }}>{context}</div>
          </a>
        </Link>
      </div>

      <style jsx>{`
        .btn {
          position: relative;
          width: 7%;
          height: 4vw;
          text-align: center;
          margin: 0 auto;

          a {
            position: absolute;
            left: 50%;
            top: 100%;
            transform: translate(-50%, 0); /* 자식요소에 translate 값 주기*/
          }

          img {
            max-height: 96px;
            height: 9vw;
          }
        }
      `}</style>
    </>
  );
}
