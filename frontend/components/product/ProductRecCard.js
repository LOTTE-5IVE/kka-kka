import ProductInfo from "./ProductInfo";
import ProductRec from "./ProductRec";

export default function ProductRecCard({ id, imgsrc, name, price, discount, imgSize }) {
  return (
    <>
      <ProductRec id={id} imgsrc={imgsrc} imgSize={imgSize} />
      <ProductInfo id={id} name={name} price={price} discount={discount} />
    </>
  );
}

ProductRecCard.defaultProps = {
  id: 1,
  imgsrc: "/sample.png",
  name: "test",
  price: 10000,
  discount: 10,
  imgSize: "300px",
};
