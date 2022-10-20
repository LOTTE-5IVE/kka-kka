import Link from "next/link";

export default function RecommRec({ id, imgsrc, name, price, discount }) {
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
        .wrapper {
          .imageSection {
            text-align: center;
            border-radius: 20px;
            background-color: #f5f5f5;

            img {
              height: 330px;
            }
          }
        }
      `}</style>
    </>
  );
}

RecommRec.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
};