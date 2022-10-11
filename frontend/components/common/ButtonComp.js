export default function ButtonComp({ context }) {
  return (
    <>
      <div className="btn">{context}</div>

      <style jsx>
        {`
          .btn {
            width: 200px;
            height: 60px;
            padding: 0px;
            line-height: 58px;
            color: #fff;
            font-size: 18px;
            background: #ff3d44;
            border-radius: 8px;
            border: 1px solid #ff3d44;
            text-align: center;
          }
        `}
      </style>
    </>
  );
}
