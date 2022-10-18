import RecommInfo from "./RecommInfo";
import RecommRec from "./RecommRec";

export default function RecommCard({ id, imgsrc, name, price, discount }) {
  return (
    <>
      <RecommRec />
      <RecommInfo />
    </>
  );
}

RecommCard.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
  name: "test",
  price: 10000,
  rate: 10,
};
