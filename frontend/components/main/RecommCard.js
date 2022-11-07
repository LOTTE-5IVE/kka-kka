import RecommInfo from "./RecommInfo";
import RecommRec from "./RecommRec";

export default function RecommCard({ id, imgsrc, name, price, discount }) {
  return (
    <>
      <RecommRec id={id} imgsrc={imgsrc}/>
      <RecommInfo id={id} name={name} price={price} discount={15}/>
    </>
  );
}

RecommCard.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
  name: "test",
  price: 10000,
  discount: 10,
};
