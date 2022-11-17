import RangeWithIcons from "./RangeWithIcons";

export default function Reviewed({ review }) {
  return (
      <>
        <RangeWithIcons
            labelText="선호도 입력"
            color={"#ffd151"}
            max={5}
            min={0.5}
            step={0.5}
            value={review.rating}
            borderColor={"#ffd151"}
            starWidth={"25px"}
        />
        <p className="contents">
          {review.contents}
        </p>
        <style jsx>
          {`

            .contents {
              color: #3e3e3e;
              font-size: 0.8rem;
            }
        `}
        </style>
      </>
  );
}
