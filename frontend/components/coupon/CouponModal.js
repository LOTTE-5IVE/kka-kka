export function CouponModal({ children }) {
  return (
    <>
      <div className="overlay">
        <div className="wrapper">
          <div className="container">{children}</div>
        </div>
      </div>

      <style jsx>{`
        .overlay {
          position: fixed;
          z-index: 9999;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
          background-color: rgba(0, 0, 0, 0.5);

          .wrapper {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);

            .container {
              margin: 40px auto 0;
              background-color: white;
              width: 400;

              border: 1px solid #dedede;
              box-shadow: 4px 5px 3px #595959;
            }
          }
        }
      `}</style>
    </>
  );
}
